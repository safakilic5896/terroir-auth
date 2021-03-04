package fr.epita.terroirauth.service;

import fr.epita.terroirauth.dao.UserDao;
import fr.epita.terroirauth.database.Utilisateur;
import fr.epita.terroirauth.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public Utilisateur getUserByEmail(String email) throws UserNotFoundException {
        final Optional<Utilisateur> optionalUtilisateur = userDao.findByEmail(email);

        if (optionalUtilisateur.isPresent()) {
            return optionalUtilisateur.get();
        } else {
            throw new UserNotFoundException("Cannot find user with email " + email);
        }
    }
}
