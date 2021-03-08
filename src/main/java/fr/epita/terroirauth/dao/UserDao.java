package fr.epita.terroirauth.dao;

import fr.epita.terroirauth.database.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String email);
}
