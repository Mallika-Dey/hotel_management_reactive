package com.example.inventoryservice.webClient;

import com.example.inventoryservice.dto.request.CheckHotelRequestDTO;
import com.example.inventoryservice.dto.response.CheckHotelResponseDTO;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface HotelServiceWebClient {
    public Mono<String> checkHotelAndRoomType(@RequestBody CheckHotelRequestDTO checkHotelRequestDTO);
}
