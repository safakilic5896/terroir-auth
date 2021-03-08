package fr.epita.terroirauth.service;

import fr.epita.terroirauth.dao.ConfirmationTokenRepository;
import fr.epita.terroirauth.database.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {

        confirmationTokenRepository.save(confirmationToken);
    }

    public void deleteById(Long id) {
        confirmationTokenRepository.deleteById(id);
    }

   public Optional<ConfirmationToken> findConfirmationTokenByToken(String token) {
        return confirmationTokenRepository.findByConfirmationToken(token);
    }

}
