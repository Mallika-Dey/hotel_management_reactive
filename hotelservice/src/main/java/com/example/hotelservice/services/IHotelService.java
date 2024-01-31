package com.example.hotelservice.services;

import com.example.hotelservice.dto.request.CreateHotelRequestDTO;
import com.example.hotelservice.entity.Hotel;
import reactor.core.publisher.Mono;

public interface IHotelService {
    Mono<Hotel> createHotel(CreateHotelRequestDTO createHotelRequestDTO);
}
