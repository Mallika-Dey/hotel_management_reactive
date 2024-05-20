package com.example.inventoryservice.mapper;

import com.example.inventoryservice.dto.request.CheckHotelRequestDTO;
import com.example.inventoryservice.dto.request.CreateHotelRoomDTO;
import com.example.inventoryservice.dto.request.UpdateHotelRequestDTO;
import com.example.inventoryservice.dto.response.CheckHotelResponseDTO;
import com.example.inventoryservice.entity.HotelDetails;
import com.example.inventoryservice.parser.ResponseParser;
import com.example.inventoryservice.webClient.HotelServiceWebClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class InventoryMapper {
    private final HotelServiceWebClient hotelServiceWebClient;

    public InventoryMapper(HotelServiceWebClient hotelServiceWebClient) {
        this.hotelServiceWebClient = hotelServiceWebClient;
    }


    public Mono<CheckHotelResponseDTO> buildInternalCallRequest(String hotelName, String hotelType) {
        CheckHotelRequestDTO request = CheckHotelRequestDTO
                .builder()
                .hotelName(hotelName)
                .roomType(hotelType)
                .build();

        return hotelServiceWebClient
                .checkHotelAndRoomType(request)
                .flatMap(response -> {
                    Integer hotelId = ResponseParser.parseValue(response, "hotelId", Integer.class);
                    Integer roomTypeId = ResponseParser.parseValue(response, "roomTypeId", Integer.class);

                    return Mono.just(
                            CheckHotelResponseDTO
                                    .builder()
                                    .hotelId(hotelId)
                                    .roomTypeId(roomTypeId)
                                    .build());
                });
    }

    public HotelDetails mapToHotel(CheckHotelResponseDTO hotelResponse,
                                    CreateHotelRoomDTO createHotelRoomDTO) {
        return HotelDetails
                .builder()
                .hotelId(hotelResponse.getHotelId())
                .roomTypeId(hotelResponse.getRoomTypeId())
                .roomCount(createHotelRoomDTO.getRoomCount())
                .price(createHotelRoomDTO.getPrice())
                .build();
    }

    public Mono<Void> updateHotelInfoInternalCall(CreateHotelRoomDTO createHotelRoomDTO) {
        UpdateHotelRequestDTO request = UpdateHotelRequestDTO
                .builder()
                .hotelName(createHotelRoomDTO.getHotelName())
                .price(createHotelRoomDTO.getPrice())
                .build();
        return hotelServiceWebClient.updateHotelPriceAndAvl(request);
    }
}
