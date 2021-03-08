package fr.epita.terroirauth.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UtilisateurDto {
    private String name;

    private String fname;

    private String email;

    private String password;

    private String pseudo;

    private String phoneNumber;

    private String role;

   private List<MarketDto> marketSelected;

}
