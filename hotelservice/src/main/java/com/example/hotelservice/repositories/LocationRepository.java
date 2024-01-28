package com.example.hotelservice.repositories;

import com.example.hotelservice.entity.Location;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface LocationRepository extends R2dbcRepository<Location, Integer> {
}
