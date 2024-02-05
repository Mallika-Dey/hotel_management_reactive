package com.example.hotelservice.services.impl;

import com.example.hotelservice.dto.request.CheckHotelRequestDTO;
import com.example.hotelservice.dto.response.CheckHotelResponseDTO;
import com.example.hotelservice.entity.Hotel;
import com.example.hotelservice.entity.RoomType;
import com.example.hotelservice.logic.HotelLogic;
import com.example.hotelservice.services.IProxyService;
import com.example.hotelservice.validator.HotelValidator;
import com.example.hotelservice.validator.RoomTypeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@RequiredArgsConstructor
public class ProxyService implements IProxyService {
    private final HotelValidator hotelValidator;
    private final RoomTypeValidator roomTypeValidator;
    private final HotelLogic hotelLogic;

    @Override
    public Mono<CheckHotelResponseDTO> checkHotelAndRoomType(
            CheckHotelRequestDTO checkHotelRequestDTO) {
        Mono<Hotel> hotel = hotelValidator
                .findByHotelName(checkHotelRequestDTO.getHotelName())
                .flatMap(hotel1 -> hotelLogic.updateHotelPriceAndAvailability(
                        hotel1, checkHotelRequestDTO));

        Mono<RoomType> roomType = roomTypeValidator
                .findByRoomTypeName(checkHotelRequestDTO.getRoomType());

        return Mono.zip(hotel, roomType).map(zipData -> mapToDto(zipData));
    }

    private CheckHotelResponseDTO mapToDto(Tuple2<Hotel, RoomType> tuple) {
        return CheckHotelResponseDTO
                .builder()
                .hotelId(tuple.getT1().getId())
                .roomTypeId(tuple.getT2().getId())
                .build();
    }
}
