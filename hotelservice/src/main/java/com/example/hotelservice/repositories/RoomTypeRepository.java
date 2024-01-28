package com.example.hotelservice.repositories;

import com.example.hotelservice.entity.Hotel;
import com.example.hotelservice.entity.RoomType;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RoomTypeRepository extends R2dbcRepository<RoomType, Long> {
}
