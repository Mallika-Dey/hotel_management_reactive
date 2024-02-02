package com.example.hotelservice.repositories;

import com.example.hotelservice.entity.Hotel;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface HotelRepository extends R2dbcRepository<Hotel, Integer> {
    Mono<Boolean> existsByName(String hotelName);

    Mono<Hotel> findByName(String hotelName);

}
