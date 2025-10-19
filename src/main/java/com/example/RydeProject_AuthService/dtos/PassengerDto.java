package com.example.RydeProject_AuthService.dtos;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {

    private long id;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    private Date cretedAt;
}
