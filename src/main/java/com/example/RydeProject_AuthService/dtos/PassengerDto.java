package com.example.RydeProject_AuthService.dtos;


import com.example.RydeProject_EntityService.models.Passenger;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    private Date cretedAt;

    public static PassengerDto from(Passenger p){
        PassengerDto result = PassengerDto.builder()
                .id(p.getId())
                .email(p.getEmail())
                .password(p.getPassword())
                .phoneNumber(p.getPhoneNumber())
                .name(p.getName())
                .build();

        return result;
    }
}
