package com.example.inventoryservice.services;

import com.example.inventoryservice.dto.request.CreateHotelRoomDTO;
import com.example.inventoryservice.dto.request.CreateRoomBookDTO;
import com.example.inventoryservice.entity.HotelDetails;
import com.example.inventoryservice.entity.RoomBook;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IHotelRoomService {
    public Mono<HotelDetails> createHotelRoom(CreateHotelRoomDTO createHotelRoomDTO);

    public Mono<RoomBook> createRoomBooked(CreateRoomBookDTO createRoomBookDTO);
//    public Mono<Void> createRoomBooked(CreateRoomBookDTO createRoomBookDTO);
}
