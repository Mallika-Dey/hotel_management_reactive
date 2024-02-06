package com.example.hotelservice.services;

import com.example.hotelservice.dto.request.CheckHotelRequestDTO;
import com.example.hotelservice.dto.request.UpdateHotelRequestDTO;
import com.example.hotelservice.dto.response.CheckHotelResponseDTO;
import reactor.core.publisher.Mono;

public interface IProxyService {
    public Mono<Void> updateHotelPriceAndAvl(UpdateHotelRequestDTO updateHotelRequestDTO);
    public Mono<CheckHotelResponseDTO> checkHotelAndRoomType(
            CheckHotelRequestDTO checkHotelRequestDTO);
}
