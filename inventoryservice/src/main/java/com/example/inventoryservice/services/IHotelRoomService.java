package com.example.inventoryservice.services;

import com.example.inventoryservice.dto.request.CreateHotelRoomDTO;
import com.example.inventoryservice.entity.HotelDetails;
import reactor.core.publisher.Mono;

public interface IHotelRoomService {
    public Mono<Object> createHotelRoom(CreateHotelRoomDTO createHotelRoomDTO);
}
