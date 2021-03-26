package fr.epita.terroirauth.util;

import fr.epita.terroirauth.database.Utilisateur;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    public String getEmailFromToken(String token) {
        String field = "email";
        return getClaimFromToken(token, field).toString();
    }

    public Object getClaimFromToken(String token, String field) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get(field);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //generate token for user
    public String generateToken(Utilisateur user) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, user);
    }

    private String doGenerateToken(Map<String, Object> claims, Utilisateur user) {

        Map<String, Object> customClaims = new HashMap<>();
        customClaims.put("email", user.getEmail());
        customClaims.put("phoneNumber", user.getPhoneNumber());
        customClaims.put("name", user.getName());
        customClaims.put("fname", user.getFname());
        customClaims.put("city", user.getCity());
        customClaims.put("codePostal", user.getCodePostal());
        customClaims.put("pseudo", user.getPseudo());
        customClaims.put("role", user.getRole());
        return Jwts.builder().setClaims(claims).addClaims(customClaims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

}
