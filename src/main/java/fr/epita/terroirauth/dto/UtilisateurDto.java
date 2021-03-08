package fr.epita.terroirauth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UtilisateurDto {
    private String name;

    private String fname;

    private String email;

    private String password;

    private String pseudo;

    private int phoneNumber;

    private String role;

}
