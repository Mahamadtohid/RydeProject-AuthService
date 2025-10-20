package com.example.RydeProject_AuthService.services;

import com.example.RydeProject_AuthService.helpers.AuthPassengerDetails;
import com.example.RydeProject_AuthService.models.Passenger;
import com.example.RydeProject_AuthService.repositories.PassengerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
* This class is responsible for loading the user in the form od userDetails Object for auth.
* */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PassengerRepository passengerRepository;

    public UserDetailsServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Passenger> passenger = passengerRepository.findPassengerByEmail(username);

        if(passenger.isPresent()){
            return new AuthPassengerDetails(passenger.get());
        }else {
            throw new UsernameNotFoundException("Cannot find the Passenger by given Email");
        }
    }
}
