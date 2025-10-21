package com.example.RydeProject_AuthService.services;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService implements CommandLineRunner {

    @Value("${jwt.expiry}") // using value from application.properties
    private int expiry;

    @Value("${jwt.secret}")
    private String SECRET;


    //creates a brand new JWT token based on payload
    public String createToken(Map<String , Object> payload , String email){

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry * 1000L);
//        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setClaims(payload)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryDate)
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS256 , SECRET)
                .compact();

    }

    public String createToken(String email){

        return createToken(new HashMap<>() , email);
    }


    public Claims extractPayload(String token){

        Claims claims = Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims;

    }



    public <T>  T extractClaim(String token , Function<Claims , T> claimResolver){
        final Claims claims = extractPayload(token);

        return claimResolver.apply(claims);
    }


    public String extractEmail(String token){

        return extractClaim(token , Claims::getSubject);
    }


    public Date extractExpirationDate(String token){
        return extractClaim(token , Claims::getExpiration);

    }



    public Boolean isTokenExpired(String token){

        return extractExpirationDate(token).before((new Date()));
    }

    public Boolean validateToken(String token , String email){
    final String userEmailFetchedEmail = extractEmail(token);
    return (userEmailFetchedEmail.equals(email)) && !isTokenExpired(token);
    }



    @Override
    public void run(String... args) throws Exception {

        Map<String , Object> map = new HashMap<>();
        map.put("email" , "a@b.com");
        map.put("phone" , "1234567891");
        String result = createToken(map , "Sadik");

        System.out.println("Generated Token is " + result);
    }
}
