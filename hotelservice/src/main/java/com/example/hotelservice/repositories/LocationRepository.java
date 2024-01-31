package com.example.hotelservice.repositories;

import com.example.hotelservice.entity.Location;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface LocationRepository extends R2dbcRepository<Location, Integer> {
    Mono<Boolean> existsByLatitudeAndLongitude(Double latitude, Double longitude);
}
