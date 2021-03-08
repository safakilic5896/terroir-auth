package fr.epita.terroirauth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectionDto {

    private String email;

    private String password;
}
