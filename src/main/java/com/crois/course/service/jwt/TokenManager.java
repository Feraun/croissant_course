package com.crois.course.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenManager {

    public static final long TOKEN_VALIDITY = 10 * 60 * 60;

    @Value("${token.signing.key}")
    private String jwtSecret;


    public String generateJwtToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(
                "roles",
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );

        return Jwts
                .builder()
                .claims().empty().add(claims)
                .and()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(getKey())
                .compact();
    }

    public Boolean validateJwtToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);

        final Claims claims = Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        Boolean isTokenExpired = claims.getExpiration().before(new Date());
        return (username.equals(userDetails.getUsername())) && !isTokenExpired;
    }

    public String getUsernameFromToken(String token) {
        final Claims claims = Jwts
                .parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }

}
