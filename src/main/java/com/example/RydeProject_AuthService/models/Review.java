package com.example.RydeProject_AuthService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@Builder //Apples the builder pattern to create an object of An class Review
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bookingreview")
@Inheritance(strategy = InheritanceType.JOINED)
public class Review extends BaseModel{

    @Column(nullable = false)
    private String content;

    private double rating;

    @OneToOne(cascade = {CascadeType.ALL} , fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    @JsonIgnore
//    @Fetch(fe)
    private Booking booking; // Defined a 1 : 1 relationship between booking and review

}
