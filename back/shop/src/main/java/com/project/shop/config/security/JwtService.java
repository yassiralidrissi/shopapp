package com.project.shop.config.security;

import com.project.shop.config.security.payload.JwtPayload;
import com.project.shop.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;


    /**
     * JWT Utils
     */

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractEmail(String token) throws IOException {
        final String subject = extractSubject(token);
        JwtPayload jwtPayload = JwtPayload.parseJson(subject);
        return jwtPayload.getEmail();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public boolean isTokenValid(String token, UserDetails userDetails) throws IOException {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    /**
     * JWT Builder
     */
    public String generateToken(User userDetails) throws IOException {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, User userDetails
    ) throws IOException {
        JwtPayload jwtPayload = buildJwtPayload(userDetails);
        return buildToken(extraClaims, jwtPayload, jwtExpiration);
    }

    private JwtPayload buildJwtPayload(User userDetails) {
        JwtPayload jwtPayload = JwtPayload
                                    .builder()
                                        .username(userDetails.getUsername())
                                        .firstname(userDetails.getFirstname())
                                        .email(userDetails.getEmail())
                                        .role(userDetails.getRole().name())
                                    .build();

        return jwtPayload;
    }

    public String generateRefreshToken(User userDetails) throws IOException {
        JwtPayload jwtPayload = buildJwtPayload(userDetails);
        return buildToken(new HashMap<>(), jwtPayload, refreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            JwtPayload jwtPayload,
            long expiration
    ) throws IOException {
        return Jwts
                .builder()
                .setHeaderParam("type", "JWT")
                .setIssuer("ShopApp.jwt")
                .setSubject(jwtPayload.toJsonString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
