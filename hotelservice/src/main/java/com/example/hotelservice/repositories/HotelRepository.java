package com.example.hotelservice.repositories;

import com.example.hotelservice.entity.Hotel;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends R2dbcRepository<Hotel, Integer> {
}
