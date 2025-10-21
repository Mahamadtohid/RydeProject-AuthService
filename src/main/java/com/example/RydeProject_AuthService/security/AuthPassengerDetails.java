package com.example.RydeProject_AuthService.helpers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.RydeProject_AuthService.models.Passenger;

import java.util.Collection;
import java.util.List;

public class AuthPassengerDetails extends Passenger implements UserDetails {


    private String username; // in Spring Security its unique called as username , it might be name , emal but Spring Security called it as username

    private String password;

    public AuthPassengerDetails(Passenger passenger){
        this.username = passenger.getEmail();
        this.password = passenger.getPassword();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    // Below Set of methods we dont need it much

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
