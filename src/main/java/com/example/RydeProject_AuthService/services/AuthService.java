package com.example.RydeProject_AuthService.services;


import com.example.RydeProject_AuthService.dtos.PassengerDto;
import com.example.RydeProject_AuthService.dtos.PassengerSignupRequestDto;
import com.example.RydeProject_AuthService.repositories.PassengerRepository;
import com.example.RydeProject_EntityService.models.Passenger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PassengerRepository passengerRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(PassengerRepository passengerRepository , BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passengerRepository = passengerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public PassengerDto signupPassenger(PassengerSignupRequestDto passengerSignupRequestDto){

        Passenger passenger = Passenger.builder()
                .email(passengerSignupRequestDto.getEmail())
                .name(passengerSignupRequestDto.getName())
                .password(bCryptPasswordEncoder.encode(passengerSignupRequestDto.getPassword()))
                .phoneNumber(passengerSignupRequestDto.getPhoneNumber())
                .build();

        Passenger newPassenger = passengerRepository.save(passenger);

        PassengerDto response = PassengerDto.from(newPassenger);

        return response;

    }
}
