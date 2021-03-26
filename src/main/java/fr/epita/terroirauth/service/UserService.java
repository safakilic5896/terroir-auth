package fr.epita.terroirauth.service;

import fr.epita.terroirauth.dao.UserDao;
import fr.epita.terroirauth.database.ConfirmationToken;
import fr.epita.terroirauth.database.Utilisateur;
import fr.epita.terroirauth.dto.ConnectionDto;
import fr.epita.terroirauth.dto.MarketDto;
import fr.epita.terroirauth.dto.UtilisateurDto;
import fr.epita.terroirauth.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String signInUser(ConnectionDto connectionDto) throws Exception {
        final Optional<Utilisateur> userAlreadyExisting = userDao.findByEmail(connectionDto.getEmail());
        Utilisateur utilisateur = null;
        if (!userAlreadyExisting.isPresent()) {
            throw new Exception("L'adresse email ou le mot de passe que vous avez saisi est incorrect. Si vous n'avez pas crée un compte veuillez en créer un.");
        }
        utilisateur = userAlreadyExisting.get();
        if (!utilisateur.isEnabled()) {
                throw new Exception("L'adresse mail n'a pas été validé.");
        }
        else if (!passwordEncoder.matches(connectionDto.getPassword(), utilisateur.getPassword()))
        {
            throw new Exception("Le mot de passe que vous avez saisi est incorrect.");
        }
        return jwtTokenUtil.generateToken(utilisateur);
    }

    public void signUpUser(UtilisateurDto utilisateurDto) throws Exception{
        final Optional<Utilisateur> userAlreadyExisting = userDao.findByEmail(utilisateurDto.getEmail());
        if (userAlreadyExisting.isPresent()) {
            throw new Exception("L'utilisateur existe déjà");
        }
        final Utilisateur utilisateur = Utilisateur.builder()
                .email(utilisateurDto.getEmail())
                .fname(utilisateurDto.getFname())
                .password(utilisateurDto.getPassword())
                .pseudo(utilisateurDto.getPseudo())
                .phoneNumber(utilisateurDto.getPhoneNumber())
                .role(utilisateurDto.getRole())
                .city(utilisateurDto.getMarketSelected().stream().map(MarketDto::getCity).collect(Collectors.joining(",")))
                .codePostal(utilisateurDto.getMarketSelected().stream().map(MarketDto::getCodePostal).collect(Collectors.joining(",")))
                .idMarkets(utilisateurDto.getMarketSelected().stream().map(marketDto -> String.valueOf(marketDto.getId())).collect(Collectors.joining(",")))
                .name(utilisateurDto.getName())
                .build();
        final String encryptedPassword = passwordEncoder.encode(utilisateur.getPassword());
        utilisateur.setPassword(encryptedPassword);
        final Utilisateur createdUser = userDao.save(utilisateur);
        final ConfirmationToken confirmationToken = new ConfirmationToken(utilisateur);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        sendConfirmationMail(utilisateur.getEmail(), confirmationToken.getConfirmationToken());
    }

    public void confirmUser(ConfirmationToken confirmationToken) {
        final Utilisateur user = confirmationToken.getUser();

        user.setEnabled(true);

        userDao.save(user);

        confirmationTokenService.deleteById(confirmationToken.getId());
    }

    public void sendConfirmationMail(String userMail, String token) {
      final SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setTo(userMail);
      mailMessage.setSubject("Lien de confirmation par e-mail! pour le site Terroir");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText(
                "Merci de votre inscription. Veuillez cliquer sur le lien ci-dessous pour activer votre compte." + "https://terroir-auth.herokuapp.com/confirm?token="
                        + token);
        emailService.sendEmail(mailMessage);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException  {
        final Optional<Utilisateur> optionalUtilisateur = userDao.findByEmail(email);

        if (optionalUtilisateur.isPresent()) {
            return optionalUtilisateur.get();
        } else {
            throw new UsernameNotFoundException("Cannot find user with email " + email);
        }
    }
}
