package fr.epita.terroirauth.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String confirmationToken;

    private LocalDate createdDate;

    @OneToOne(targetEntity = Utilisateur.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "utilisateur_id")
    private Utilisateur user;

    public ConfirmationToken(Utilisateur utilisateur) {
        this.user = utilisateur;
        this.createdDate = LocalDate.now();
        this.confirmationToken = UUID.randomUUID().toString();
    }

}
