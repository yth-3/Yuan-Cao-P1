package org.revature.p1.services;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.revature.p1.dtos.responses.Principal;
import org.revature.p1.utils.JwtConfig;

import java.util.Date;

public class TokenService {
    private JwtConfig jwtConfig;

    public TokenService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(Principal principal) {
        long now = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()
                .setId(principal.getId())
                .setIssuer("ERS")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getTimeToExpiration()))
                .setSubject(principal.getForename())
                .claim("role", principal.getType())
                .signWith(jwtConfig.getAlgorithm(), jwtConfig.getSigningKey());

        return builder.compact();
    }
}
