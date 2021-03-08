package fr.epita.terroirauth.service;

import fr.epita.terroirauth.database.ConfirmationToken;
import fr.epita.terroirauth.database.Utilisateur;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class PostServiceEmailInscription {

    @Value("${server-back}")
    String url;

    private RestTemplate restTemplate;

    public ResponseEntity postEmailInscription(ConfirmationToken confirmationToken) {
        Utilisateur utilisateur = confirmationToken.getUser();
        restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, Object> map = new HashMap<>();
        map.put("city", utilisateur.getCity());
        map.put("codePostal", utilisateur.getCodePostal());
        map.put("email", utilisateur.getEmail());
        map.put("role", utilisateur.getRole());
        map.put("idMarkets", utilisateur.getIdMarkets());
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        ResponseEntity response = this.restTemplate.postForEntity(url, entity, ResponseEntity.class);
        return response;

    }
}
