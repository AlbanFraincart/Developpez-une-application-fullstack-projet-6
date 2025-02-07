package com.openclassrooms.mddapi.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Génère un token JWT à partir de l'objet Authentication.
     */
    public String generateJwtToken(Authentication authentication) {
//        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        // Récupère la clé à partir de la chaîne Base64
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));

        return Jwts.builder()
                .setSubject(userPrincipal.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                // Utilise signWith(key) pour déduire automatiquement l'algorithme
                .signWith(key)
                .compact();
    }

    /**
     * Extrait le nom d'utilisateur (subject) du token JWT.
     */
    public String getUserNameFromJwtToken(String token) {
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Valide le token JWT.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Le token n'est pas valide
            System.err.println("JWT validation error: " + e.getMessage());
        }
        return false;
    }
}
