package com.example.RydeProject_AuthService.controllers;


import com.example.RydeProject_AuthService.dtos.PassengerDto;
import com.example.RydeProject_AuthService.dtos.PassengerSignupRequestDto;
import com.example.RydeProject_AuthService.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.RydeProject_AuthService.dtos.PassengerSignupRequestDto;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){

        PassengerDto response = authService.signupPassenger(passengerSignupRequestDto);

        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }
}
