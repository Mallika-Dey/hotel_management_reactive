package com.example.hotelservice.controller;

import com.example.hotelservice.dto.request.CheckHotelRequestDTO;
import com.example.hotelservice.dto.request.UpdateHotelRequestDTO;
import com.example.hotelservice.dto.response.CheckHotelResponseDTO;
import com.example.hotelservice.response.ReactiveResponseHandler;
import com.example.hotelservice.services.IProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v2/proxy")
@RequiredArgsConstructor
public class ProxyController {
    private final IProxyService proxyService;

    @PostMapping("/get/hotel_name_room_type")
    public Mono<CheckHotelResponseDTO> getHotelNameAndType(
            @RequestBody CheckHotelRequestDTO checkHotelRequestDTO) {
        return proxyService.checkHotelAndRoomType(checkHotelRequestDTO);
    }

    @PostMapping("/update/hotel-info")
    public Mono<Void> updateHotelPriceAndAvl(
            @RequestBody UpdateHotelRequestDTO updateHotelRequestDTO) {
        //   return proxyService.updateHotelPriceAndAvl(updateHotelRequestDTO);
        System.out.println("reached....................");
        return Mono.empty();
    }
}
