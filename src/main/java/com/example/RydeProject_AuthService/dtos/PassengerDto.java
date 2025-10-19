package com.example.RydeProject_AuthService.dtos;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {


    private String name;

    private String email;

    private String password;
}
