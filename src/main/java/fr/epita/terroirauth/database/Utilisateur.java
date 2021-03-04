package fr.epita.terroirauth.database;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String fname;

    private String email;

    private String password;

    private String pseudo;

    private int phoneNumber;

    private String role;

    @Builder.Default
    private boolean enabled = false;
}
