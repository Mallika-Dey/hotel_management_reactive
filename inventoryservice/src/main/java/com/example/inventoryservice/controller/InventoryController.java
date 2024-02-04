package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.request.CreateHotelRoomDTO;
import com.example.inventoryservice.response.ReactiveResponseHandler;
import com.example.inventoryservice.services.IHotelRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final IHotelRoomService hotelRoomService;

    public InventoryController(IHotelRoomService hotelRoomService) {
        this.hotelRoomService = hotelRoomService;
    }

    @PostMapping("/create-room")
    public Mono<ResponseEntity<Object>> createHotelRoom(@RequestBody CreateHotelRoomDTO hotelRoomDTO){
        return hotelRoomService.createHotelRoom(hotelRoomDTO)
                .map(hotelDetails -> ReactiveResponseHandler.generateResponse("Room create Successfully", HttpStatus.CREATED));
    }
}
