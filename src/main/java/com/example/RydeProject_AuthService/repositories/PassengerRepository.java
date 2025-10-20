package com.example.RydeProject_AuthService.repositories;

import com.example.RydeProject_AuthService.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PassengerRepository extends JpaRepository<Passenger , Long> {

}
