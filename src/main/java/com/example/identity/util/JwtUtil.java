package com.example.identity.util;

import com.example.identity.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private final SecretKey key;
    private final long expiration;

    public JwtUtil(@Value("${jwt-secret}") String secret,
                   @Value("${jwt-expiration}") long expiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        if (expiration <= 0) throw new IllegalArgumentException("Expiration must be positive");
        this.expiration = expiration;
    }

    public String generateToken(User user) {
        Date now = new Date();
        return Jwts.builder().setSubject(user.getUsername())
            .claim("role", user.getRole()).setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + expiration))
            .signWith(key, SignatureAlgorithm.HS256).compact();
    }
}
