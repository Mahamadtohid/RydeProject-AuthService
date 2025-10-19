package com.example.RydeProject_AuthService.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking extends BaseModel{

    //    @OneToOne(cascade ={ CascadeType.PERSIST , CascadeType.REMOVE})
//    private Review review;
    @OneToOne(mappedBy = "booking")
    private Review review;

    @Enumerated(value = EnumType.STRING)// Stores enum as it is  string in database default in tinyint
    private BookingStatus bookingStatus;

    @Temporal(value = TemporalType.TIMESTAMP) // By default its timestamp itself but we are entioning specifically
    private Date startTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date endTime;

    private Long totalDistance;

    @ManyToOne
    private Driver driver;

    @ManyToOne
    private Passenger passenger;

}

