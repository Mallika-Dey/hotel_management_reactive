package com.example.inventoryservice.services.impl;

import com.example.inventoryservice.dto.request.CreateHotelRoomDTO;
import com.example.inventoryservice.dto.request.CreateRoomBookDTO;
import com.example.inventoryservice.dto.response.CheckHotelResponseDTO;
import com.example.inventoryservice.entity.HotelDetails;
import com.example.inventoryservice.entity.RoomBook;
import com.example.inventoryservice.logic.InventoryLogic;
import com.example.inventoryservice.mapper.InventoryMapper;
import com.example.inventoryservice.services.IHotelRoomService;
import com.example.inventoryservice.validator.HotelRoomValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class HotelRoomService implements IHotelRoomService {

    private final InventoryMapper inventoryMapper;
    private final InventoryLogic inventoryLogic;
    private final HotelRoomValidator hotelRoomValidator;
    private static final Logger logger = LoggerFactory.getLogger(HotelRoomService.class);

    public HotelRoomService(InventoryMapper inventoryMapper,
                            InventoryLogic inventoryLogic, HotelRoomValidator hotelRoomValidator) {
        this.inventoryMapper = inventoryMapper;
        this.inventoryLogic = inventoryLogic;
        this.hotelRoomValidator = hotelRoomValidator;
    }

    public Mono<HotelDetails> createHotelRoom(CreateHotelRoomDTO createHotelRoomDTO) {

        return inventoryMapper.buildInternalCallRequest(
                        createHotelRoomDTO.getHotelName(), createHotelRoomDTO.getRoomType())
                .flatMap(hotelResponse -> {
                    return inventoryLogic.dbOperation(hotelResponse, createHotelRoomDTO);
                })
                .doOnRequest(l -> logger.debug("Hotel Create start processing"))
                .doOnSuccess(hotel -> {
                    logger.info("Hotel info update in hotel microservice");
                });
    }

    public Mono<Void> createRoomBooked(CreateRoomBookDTO createRoomBookDTO) {
        Mono<Integer> p = roomBookValidation(createRoomBookDTO);
//        return null;
        return Mono.empty();
    }

    public Mono<Integer> roomBookValidation(CreateRoomBookDTO createRoomBookDTO) {
        return inventoryMapper.buildInternalCallRequest(
                createRoomBookDTO.getHotelName(), createRoomBookDTO.getRoomType()
        ).flatMap(hotelAndRoomID -> {
            return hotelRoomValidator.isHotelRoomExists(hotelAndRoomID.getHotelId(), hotelAndRoomID.getRoomTypeId());
        });
    }
}
