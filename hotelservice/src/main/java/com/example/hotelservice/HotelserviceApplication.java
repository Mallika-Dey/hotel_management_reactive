package com.example.hotelservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class HotelserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelserviceApplication.class, args);
	}

}
