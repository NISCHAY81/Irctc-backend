package com.substring.irctc.config.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class JwtHelper {
    private  static final long JWT_VALIDITY_SECONDS = 60 * 60 * 1000;
    private final String SECRET = "ashklhqeihweiyrryiewyfnsdcbmncxbasjhssdadfhodsfhidfdiyfiaklkhddfihwwfohdsahnfdsbkjaeefo";

    private Key key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // generate token
    public String generateToken(UserDetails  userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_VALIDITY_SECONDS))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // get Username from Token
    public String getUsernameFromToken(String token) {
        return  getClaims(token).getSubject();
    }

    // validate Token
    public  boolean isTokenValid(String token, UserDetails  userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
// get all claims from token

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody();

    }

}
