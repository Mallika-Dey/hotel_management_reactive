package com.example.inventoryservice.services.impl;

import com.example.inventoryservice.dto.request.CheckHotelRequestDTO;
import com.example.inventoryservice.dto.request.CreateHotelRoomDTO;
import com.example.inventoryservice.dto.request.UpdateHotelRequestDTO;
import com.example.inventoryservice.dto.response.CheckHotelResponseDTO;
import com.example.inventoryservice.entity.HotelDetails;
import com.example.inventoryservice.parser.ResponseParser;
import com.example.inventoryservice.repositories.HotelDetailsRepository;
import com.example.inventoryservice.services.IHotelRoomService;
import com.example.inventoryservice.validator.HotelRoomValidator;
import com.example.inventoryservice.webClient.HotelServiceWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class HotelRoomService implements IHotelRoomService {

    private final HotelDetailsRepository hotelDetailsRepository;
    private final HotelServiceWebClient hotelServiceWebClient;
    private final HotelRoomValidator hotelRoomValidator;
    private static final Logger logger = LoggerFactory.getLogger(HotelRoomService.class);

    public HotelRoomService(HotelDetailsRepository hotelDetailsRepository, HotelServiceWebClient hotelServiceWebClient, HotelRoomValidator hotelRoomValidator) {
        this.hotelDetailsRepository = hotelDetailsRepository;
        this.hotelServiceWebClient = hotelServiceWebClient;
        this.hotelRoomValidator = hotelRoomValidator;
    }

    public Mono<HotelDetails> createHotelRoom(CreateHotelRoomDTO createHotelRoomDTO) {

        return buildInternalCallRequest(createHotelRoomDTO)
                .flatMap(hotelResponse -> {
                    return dbOperation(hotelResponse, createHotelRoomDTO);
                })
                .doOnRequest(l -> logger.debug("Hotel Create start processing"))
                .doOnSuccess(hotel -> {
                    logger.info("Hotel info update in hotel microservice");
                });
    }

    private Mono<HotelDetails> dbOperation(CheckHotelResponseDTO hotelResponse, CreateHotelRoomDTO createHotelRoomDTO) {
        Mono<Boolean> validate = hotelRoomValidator
                .validateHotelByRoomType(hotelResponse.getHotelId(), hotelResponse.getRoomTypeId());
        Mono<HotelDetails> hotelDetailsMono = hotelDetailsRepository
                .save(mapToHotel(hotelResponse, createHotelRoomDTO))
                .cache();
        Mono<Void> updateHotel = updateHotelInfoInternalCall(createHotelRoomDTO);

        return Mono.zip(validate, hotelDetailsMono, updateHotel).then(hotelDetailsMono);
    }

    private HotelDetails mapToHotel(CheckHotelResponseDTO hotelResponse,
                                    CreateHotelRoomDTO createHotelRoomDTO) {
        return HotelDetails
                .builder()
                .hotelId(hotelResponse.getHotelId())
                .roomTypeId(hotelResponse.getRoomTypeId())
                .roomCount(createHotelRoomDTO.getRoomCount())
                .price(createHotelRoomDTO.getPrice())
                .build();
    }

    private Mono<CheckHotelResponseDTO> buildInternalCallRequest(CreateHotelRoomDTO createHotelRoomDTO) {
        CheckHotelRequestDTO request = CheckHotelRequestDTO
                .builder()
                .hotelName(createHotelRoomDTO.getHotelName())
                .roomType(createHotelRoomDTO.getRoomType())
                .price(createHotelRoomDTO.getPrice())
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

    private Mono<Void> updateHotelInfoInternalCall(CreateHotelRoomDTO createHotelRoomDTO) {
        UpdateHotelRequestDTO request = UpdateHotelRequestDTO
                .builder()
                .hotelName(createHotelRoomDTO.getHotelName())
                .price(createHotelRoomDTO.getPrice())
                .build();
        return hotelServiceWebClient.updateHotelPriceAndAvl(request);
    }
}
