package com.infy.pinterest.config;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.infy.pinterest.exception.InfyPinterestException;

import io.jsonwebtoken.*;

import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    // in-memory blacklist (optional)
    private final Set<String> invalidatedTokens = ConcurrentHashMap.newKeySet();

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // generate JWT token
    public String generateToken(String username, String roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // get username from token
    public String getUsername(String token) {
        return parseClaims(token).getSubject();
    }

    // get roles
    public String getRoles(String token) {
        return parseClaims(token).get("roles", String.class);
    }

    // validate token
    public boolean validateToken(String token) {
        if (invalidatedTokens.contains(token)) {
            return false;
        }

        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new InfyPinterestException("Service.TOKEN_EXPIRED");
        } catch (JwtException e) {
            throw new InfyPinterestException("Service.INVALID_TOKEN");
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // logout support
    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }
}
