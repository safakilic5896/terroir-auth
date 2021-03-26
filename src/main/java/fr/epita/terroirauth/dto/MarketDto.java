package fr.epita.terroirauth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarketDto {

    private long id;

    private String adress;

    private String city;

    private String codePostal;

    private String description;

    private String name;
}
