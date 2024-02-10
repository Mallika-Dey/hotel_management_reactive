package com.example.inventoryservice.webClient;

import com.example.inventoryservice.dto.request.CheckHotelRequestDTO;
import com.example.inventoryservice.dto.request.UpdateHotelRequestDTO;
import com.example.inventoryservice.dto.response.CheckHotelResponseDTO;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface HotelServiceWebClient {
    public Mono<Void> updateHotelPriceAndAvl(@RequestBody UpdateHotelRequestDTO updateHotelRequestDTO);
    public Mono<String> checkHotelAndRoomType(@RequestBody CheckHotelRequestDTO checkHotelRequestDTO);
}
