package meu_pet_saude.app.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import meu_pet_saude.app.model.Tutor;

@Service
public class TokenService {
    
    public String gerarToken(Tutor tutor) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        return JWT.create()
                  .withIssuer(TOKEN_ISSUER)
                  .withSubject(tutor.getEmail())
                  .withExpiresAt(_gerarDataExpiracao())
                  .withClaim("nome", tutor.getNome())
                  .sign(algorithm);
    }

    public String obterEmailTutor(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        return JWT.require(algorithm)
                  .withIssuer(TOKEN_ISSUER)
                  .build()
                  .verify(token)
                  .getSubject();
    }

    public Instant _gerarDataExpiracao(){
        return LocalDateTime.now().plusDays(30).toInstant(ZoneOffset.of("-03:00"));
    }


    @Value("${spring.security.secret-key}")
    private String SECRET_KEY;

    @Value("${spring.security.token-issuer}")
    private String TOKEN_ISSUER;
}
