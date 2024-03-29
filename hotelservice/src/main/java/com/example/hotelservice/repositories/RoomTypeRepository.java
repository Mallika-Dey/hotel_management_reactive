package com.example.hotelservice.repositories;

import com.example.hotelservice.entity.RoomType;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RoomTypeRepository extends R2dbcRepository<RoomType, Integer> {
    Mono<RoomType> findByRoomType(String roomType);
}
