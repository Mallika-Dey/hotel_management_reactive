package com.example.inventoryservice.repositories;

import com.example.inventoryservice.entity.HotelDetails;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface HotelDetailsRepository extends R2dbcRepository<HotelDetails, Integer> {
    Mono<Boolean> existsByHotelIdAndRoomTypeId(Integer hotelId, Integer roomTypeId);
    Mono<HotelDetails> findByHotelIdAndRoomTypeId(Integer hotelId, Integer roomTypeId);

}
