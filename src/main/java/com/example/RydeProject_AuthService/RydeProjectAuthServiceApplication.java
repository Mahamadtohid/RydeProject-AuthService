package com.example.RydeProject_AuthService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan("com.example.RydeProject_EntityService.models")
public class RydeProjectAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RydeProjectAuthServiceApplication.class, args);
	}

}
