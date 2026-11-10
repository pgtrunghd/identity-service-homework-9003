package com.example.identity.util;

import com.example.identity.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {
    @Test
    void signsTokenWithUsernameRoleAndOneHourExpiry() throws Exception {
        String secret = "identity-service-demo-key-256-bits!";
        String token = new JwtUtil(secret, 3600000).generateToken(new User("alice", "hashed", "USER"));
        var claims = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
            .build().parseClaimsJws(token).getBody();
        assertEquals("alice", claims.getSubject());
        assertEquals("USER", claims.get("role"));
        assertEquals(3600000, claims.getExpiration().getTime() - claims.getIssuedAt().getTime());
        assertEquals(3, token.split("\\.").length);
        java.nio.file.Files.writeString(java.nio.file.Path.of("build/demo-token.txt"), token);
    }
}
