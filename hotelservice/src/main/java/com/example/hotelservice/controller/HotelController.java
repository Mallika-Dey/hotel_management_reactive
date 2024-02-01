package com.example.hotelservice.controller;

import com.example.hotelservice.dto.request.CreateHotelRequestDTO;
import com.example.hotelservice.response.ReactiveResponseHandler;
import com.example.hotelservice.services.IHotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
public class HotelController {
    private final IHotelService hotelService;

    @PostMapping("/create")
    public Mono<ResponseEntity<Object>> createHotel(
            @RequestBody CreateHotelRequestDTO hotelRequestDTO) {
        return hotelService.createHotel(hotelRequestDTO)
                .map(hotel -> ReactiveResponseHandler.generateResponse
                        ("Hotel created successfully", HttpStatus.CREATED, hotel));
    }
}
