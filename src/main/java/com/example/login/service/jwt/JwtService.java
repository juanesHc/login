package com.example.login.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private String jwtExpiration;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateToken(UUID personId, String email, String givenName, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", personId.toString());
        claims.put("email", email);
        claims.put("givenName", givenName);
        claims.put("role", role);

        return buildToken(claims, email);
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String buildToken(Map<String, Object> extraClaims, String subject) {
        long currentTimeMillis = System.currentTimeMillis();

        return Jwts.builder()
                .claims(extraClaims)
                .subject(subject)
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
