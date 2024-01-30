package com.example.hotelservice.services;

import com.example.hotelservice.dto.request.CreateRoomTypeDTO;
import com.example.hotelservice.entity.RoomType;
import reactor.core.publisher.Mono;

public interface IRoomTypeService {
    Mono<RoomType> createRoomType(CreateRoomTypeDTO createRoomTypeDTO);
}
