package com.example.inventoryservice.services.impl;

import com.example.inventoryservice.dto.request.CheckHotelRequestDTO;
import com.example.inventoryservice.dto.request.CreateHotelRoomDTO;
import com.example.inventoryservice.dto.response.CheckHotelResponseDTO;
import com.example.inventoryservice.entity.HotelDetails;
import com.example.inventoryservice.repositories.HotelDetailsRepository;
import com.example.inventoryservice.services.IHotelRoomService;
import com.example.inventoryservice.webClient.HotelServiceWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class HotelRoomService implements IHotelRoomService {

    private final HotelDetailsRepository hotelDetailsRepository;
    private final HotelServiceWebClient hotelServiceWebClient;
    private static final Logger logger = LoggerFactory.getLogger(HotelRoomService.class);

    public HotelRoomService(HotelDetailsRepository hotelDetailsRepository, HotelServiceWebClient hotelServiceWebClient) {
        this.hotelDetailsRepository = hotelDetailsRepository;
        this.hotelServiceWebClient = hotelServiceWebClient;
    }

    public Mono<HotelDetails> createHotelRoom(CreateHotelRoomDTO createHotelRoomDTO){
        //Mono<CheckHotelResponseDTO> responseDTO = buildInternalCallRequest(createHotelRoomDTO);
        return buildInternalCallRequest(createHotelRoomDTO)
                .flatMap(hotelResponse -> {
                   return hotelDetailsRepository.save(mapToHotel(hotelResponse, createHotelRoomDTO));
                })
                .doOnRequest(l -> logger.debug("Hotel Create start processing"))
                .doOnSuccess(hotel -> logger.info("Hotel {} saved successfully"));
    }

    private HotelDetails mapToHotel(CheckHotelResponseDTO  hotelResponse,
                                          CreateHotelRoomDTO createHotelRoomDTO){
        return HotelDetails
                .builder()
                .hotelId(hotelResponse.getHotelId())
                .roomTypeId(hotelResponse.getRoomTypeId())
                .roomCount(createHotelRoomDTO.getRoomCount())
                .price(createHotelRoomDTO.getRoomCount())
                .build();
    }

    private Mono<CheckHotelResponseDTO> buildInternalCallRequest(CreateHotelRoomDTO createHotelRoomDTO){  // will chnage return type to CheckHotelREsponseDTO
       CheckHotelRequestDTO request = CheckHotelRequestDTO
               .builder()
               .hotelName(createHotelRoomDTO.getHotelName())
               .roomType(createHotelRoomDTO.getRoomType())
               .build();

      return hotelServiceWebClient.checkHotelAndRoomType(request);
    }
}
