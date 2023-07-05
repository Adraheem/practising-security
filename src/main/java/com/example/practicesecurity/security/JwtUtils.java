package com.example.practicesecurity.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${security.secret}")
    private String jwtSecret;

    public String generateToken(String username) {
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + (1000 * 3600 * 24 * 3));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .setExpiration(expirationDate)
                .setSubject(username)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Invalid token");
        }
    }

    public String extractUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody();

        return claims.getSubject();
    }
}
