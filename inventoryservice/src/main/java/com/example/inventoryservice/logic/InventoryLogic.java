package com.example.inventoryservice.logic;

import com.example.inventoryservice.dto.request.CreateHotelRoomDTO;
import com.example.inventoryservice.dto.response.CheckHotelResponseDTO;
import com.example.inventoryservice.entity.HotelDetails;
import com.example.inventoryservice.mapper.InventoryMapper;
import com.example.inventoryservice.repositories.HotelDetailsRepository;
import com.example.inventoryservice.validator.HotelRoomValidator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class InventoryLogic {
    private final HotelRoomValidator hotelRoomValidator;
    private final HotelDetailsRepository hotelDetailsRepository;
    private final InventoryMapper inventoryMapper;

    public InventoryLogic(HotelRoomValidator hotelRoomValidator, HotelDetailsRepository hotelDetailsRepository, InventoryMapper inventoryMapper) {
        this.hotelRoomValidator = hotelRoomValidator;
        this.hotelDetailsRepository = hotelDetailsRepository;
        this.inventoryMapper = inventoryMapper;
    }

    public Mono<HotelDetails> dbOperation(CheckHotelResponseDTO hotelResponse, CreateHotelRoomDTO createHotelRoomDTO) {
        Mono<Boolean> validate = hotelRoomValidator
                .validateHotelByRoomType(hotelResponse.getHotelId(), hotelResponse.getRoomTypeId());
        Mono<HotelDetails> hotelDetailsMono = hotelDetailsRepository
                .save(inventoryMapper.mapToHotel(hotelResponse, createHotelRoomDTO))
                .cache();
        Mono<Void> updateHotel = inventoryMapper.updateHotelInfoInternalCall(createHotelRoomDTO);

        return Mono.zip(validate, hotelDetailsMono, updateHotel).then(hotelDetailsMono);
    }
}
