package com.example.RydeProject_AuthService.services;


import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService implements CommandLineRunner {

    @Value("${jwt.expiry}") // using value from application.properties
    private int expiry;

    @Value("${jwt.secret}")
    private String SECRET;


    //creates a brand new JWT token based on payload
    private String createToken(Map<String , Object> payload , String username){

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry * 1000L);
//        SecretKey key = Keys./hma;

        return Jwts.builder()
                .setClaims(payload)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryDate)
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256 , SECRET)
                .compact();

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
