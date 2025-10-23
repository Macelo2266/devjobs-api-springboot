package br.com.devjobs.api.devjobs_api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    // Gera o token JWT com username e role
    public String generateToken(UserDetails user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Pega a primeira role do usuário (exemplo: ROLE_ADMIN)
            String role = user.getAuthorities().stream()
                    .findFirst()
                    .map(Object::toString)
                    .orElse("ROLE_USER");

            return JWT.create()
                    .withIssuer("devjobs-api")
                    .withSubject(user.getUsername())
                    .withClaim("role", role) // adiciona a role no token
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

        } catch (Exception exception) {
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    // Define a data de expiração do token (2 horas)
    private Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    // Retorna o username (subject) armazenado no token
    public String getUsernameFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("devjobs-api")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            return null; // token inválido
        }
    }

    // Verifica se o token é válido e pertence ao usuário
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username != null && username.equals(userDetails.getUsername());
    }

    public DecodedJWT decodeToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("devjobs-api")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            return null;
        }
    }

}
