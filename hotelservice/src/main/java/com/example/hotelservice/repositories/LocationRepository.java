package com.example.hotelservice.repositories;

import com.example.hotelservice.entity.Location;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface LocationRepository extends R2dbcRepository<Location, Integer> {
    Mono<Boolean> existsByDistrict(String district);
    Mono<Location> findByDistrict(String district);
  //  Mono<Location> findById(Integer id);
}