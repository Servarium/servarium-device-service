package app.servarium.adapter.restapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.function.Function;

@Component
public class AccessTokenProcessor {
    @Value("${app.jwt.accessPublicKey}")
    private String publicKeyPlain;

    private PublicKey publicKey;

    @PostConstruct
    public void init() {
        publicKey = RsaUtils.generatePublicKey(publicKeyPlain);
    }

    boolean isValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    long getSubject(String token) {
        String sub = extractClaims(token, publicKey, Claims::getSubject);
        return Long.parseLong(sub);
    }

    String getRole(String token) {
        return extractClaims(token, publicKey, claims -> claims.get("role", String.class));
    }

    private <T> T extractClaims(String token, PublicKey key, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token, key);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, PublicKey publicKey) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}