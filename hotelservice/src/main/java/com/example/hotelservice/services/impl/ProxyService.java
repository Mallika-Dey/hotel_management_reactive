package com.example.hotelservice.services.impl;

import com.example.hotelservice.dto.request.CheckHotelRequestDTO;
import com.example.hotelservice.dto.request.UpdateHotelRequestDTO;
import com.example.hotelservice.dto.response.CheckHotelResponseDTO;
import com.example.hotelservice.entity.Hotel;
import com.example.hotelservice.entity.RoomType;
import com.example.hotelservice.logic.HotelLogic;
import com.example.hotelservice.services.IProxyService;
import com.example.hotelservice.validator.HotelValidator;
import com.example.hotelservice.validator.RoomTypeValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@RequiredArgsConstructor
public class ProxyService implements IProxyService {
    private final Logger LOGGER = LoggerFactory.getLogger("LocationService.class");
    private final HotelValidator hotelValidator;
    private final RoomTypeValidator roomTypeValidator;
    private final HotelLogic hotelLogic;


    public Mono<Void> updateHotelPriceAndAvl(UpdateHotelRequestDTO updateHotelRequestDTO) {
        return hotelValidator.findByHotelName(updateHotelRequestDTO.getHotelName())
                .flatMap(hotel -> hotelLogic.updateHotelPriceAndAvailability(hotel, updateHotelRequestDTO))
                .then()
                .doOnSuccess(hotel -> LOGGER.info("Hotel info updated successfully"));
    }


    @Override
    public Mono<CheckHotelResponseDTO> checkHotelAndRoomType(
            CheckHotelRequestDTO checkHotelRequestDTO) {

        Mono<Hotel> hotel = hotelValidator
                .findByHotelName(checkHotelRequestDTO.getHotelName());

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
