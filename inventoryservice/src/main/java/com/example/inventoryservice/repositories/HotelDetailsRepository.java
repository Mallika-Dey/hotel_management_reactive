package com.example.inventoryservice.repositories;

import com.example.inventoryservice.entity.HotelDetails;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface HotelDetailsRepository extends R2dbcRepository<HotelDetails, Integer> {
}
