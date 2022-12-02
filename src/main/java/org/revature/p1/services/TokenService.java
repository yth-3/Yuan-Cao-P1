package org.revature.p1.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.revature.p1.dtos.responses.Principal;
import org.revature.p1.utils.JwtConfig;
import org.revature.p1.utils.enums.ClientUserType;

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

    public Principal extractPrincipal(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();
            return new Principal(claims.getId(),
                                 claims.getSubject(),
                                 ClientUserType.valueOf(claims.get("role", String.class)),
                                 false,
                                 claims.getIssuedAt().getTime(),
                                 claims.getExpiration().getTime());
        } catch (Exception e) {
            return null;
        }
    }

    public ClientUserType extractUserType(String token) {
        Principal principal = extractPrincipal(token);
        if (principal == null) {
            return null;
        } else {
            return principal.getType();
        }
    }

    public String extractUserId(String token) {
        Principal principal = extractPrincipal(token);
        if (principal == null) {
            return null;
        } else {
            return principal.getId();
        }
    }
}
