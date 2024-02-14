package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.request.CreateHotelRoomDTO;
import com.example.inventoryservice.dto.request.CreateRoomBookDTO;
import com.example.inventoryservice.entity.RoomBook;
import com.example.inventoryservice.response.ReactiveResponseHandler;
import com.example.inventoryservice.services.IHotelRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final IHotelRoomService hotelRoomService;

    public InventoryController(IHotelRoomService hotelRoomService) {
        this.hotelRoomService = hotelRoomService;
    }

    @PostMapping("/create-room")
    public Mono<ResponseEntity<Object>> createHotelRoom(@RequestBody CreateHotelRoomDTO hotelRoomDTO) {
        return hotelRoomService.createHotelRoom(hotelRoomDTO)
                .map(hotelDetails -> ReactiveResponseHandler.generateResponse("Room create Successfully", HttpStatus.CREATED));
    }

    @PostMapping("/room-book")
    public Mono<ResponseEntity<Object>> createRoomBook(@RequestBody CreateRoomBookDTO roomBookDTO) {
        return hotelRoomService.createRoomBooked(roomBookDTO)
                .map(response -> ReactiveResponseHandler.generateResponse("successfully booked room", HttpStatus.CREATED, response));

//        return hotelRoomService.createRoomBooked(roomBookDTO)
//                .collectList()
//                .map(roomBooks -> {
//                    List<Integer> roomBookIds = roomBooks.stream()
//                            .map(RoomBook::getId)
//                            .collect(Collectors.toList());
//                    return ReactiveResponseHandler.generateResponse(
//                            "Room Booked Successfully",
//                            HttpStatus.CREATED,
//                            roomBookIds);
//                });
    }
}
