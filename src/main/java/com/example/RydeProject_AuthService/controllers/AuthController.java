package com.example.RydeProject_AuthService.controllers;


import com.example.RydeProject_AuthService.dtos.*;
import com.example.RydeProject_AuthService.services.AuthService;
import com.example.RydeProject_AuthService.services.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.example.RydeProject_AuthService.dtos.PassengerSignupRequestDto;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Value("${cookie.expiry}")
    private int cookieExpiry;
    private AuthService authService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager , JwtService jwtService){
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){

        PassengerDto response = authService.signupPassenger(passengerSignupRequestDto);

        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto , HttpServletResponse response){
        System.out.println("Request Received...........");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));

        if(authentication.isAuthenticated()){
            String jwtToken = jwtService.createToken(authRequestDto.getEmail());
            ResponseCookie cookie = ResponseCookie.from("Jwt Token", jwtToken)
                            .httpOnly(true)
                            .secure(false)
                            .path("/")
                            .maxAge(cookieExpiry)
                            .build();
            response.setHeader(HttpHeaders.SET_COOKIE , cookie.toString());
            System.out.println(jwtToken);
            return new ResponseEntity<>(AuthResponseDto.builder().success(true).build() , HttpStatus.OK);
        }
        else{
            throw new UsernameNotFoundException("User Not Found");
        }


    }
}
