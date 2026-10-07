package br.com.gerenciamento_cursos.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    public String gerarToken(UserDetails userDetails) {
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(expirationMs);

        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .sign(Algorithm.HMAC256(secret));
    }

    public boolean tokenValido(String token, UserDetails userDetails) {
        try {
            String username = extrairUsername(token);
            return username.equals(userDetails.getUsername());

        } catch (JWTVerificationException ex) {
            return false;
        }

    }
    public String extrairUsername(String token) {
        return verificarToken(token).getSubject();
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    private DecodedJWT verificarToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm).build().verify(token);
    }
}
