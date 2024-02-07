package com.example.inventoryservice.services.impl;

import com.example.inventoryservice.dto.request.CheckHotelRequestDTO;
import com.example.inventoryservice.dto.request.CreateHotelRoomDTO;
import com.example.inventoryservice.dto.response.CheckHotelResponseDTO;
import com.example.inventoryservice.entity.HotelDetails;
import com.example.inventoryservice.parser.ResponseParser;
import com.example.inventoryservice.repositories.HotelDetailsRepository;
import com.example.inventoryservice.services.IHotelRoomService;
import com.example.inventoryservice.webClient.HotelServiceWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class HotelRoomService implements IHotelRoomService {

    private final HotelDetailsRepository hotelDetailsRepository;
    private final HotelServiceWebClient hotelServiceWebClient;
    private final ResponseParser responseParser;
    private static final Logger logger = LoggerFactory.getLogger(HotelRoomService.class);

    public HotelRoomService(HotelDetailsRepository hotelDetailsRepository, HotelServiceWebClient hotelServiceWebClient, ResponseParser responseParser) {
        this.hotelDetailsRepository = hotelDetailsRepository;
        this.hotelServiceWebClient = hotelServiceWebClient;
        this.responseParser = responseParser;
    }

    public Mono<Object> createHotelRoom(CreateHotelRoomDTO createHotelRoomDTO){
        //Mono<CheckHotelResponseDTO> responseDTO = buildInternalCallRequest(createHotelRoomDTO);
        return buildInternalCallRequest(createHotelRoomDTO);
//                .flatMap(hotelResponse -> {
//                   return hotelDetailsRepository.save(mapToHotel(hotelResponse, createHotelRoomDTO));
//                })
//                .doOnRequest(l -> logger.debug("Hotel Create start processing"))
//                .doOnSuccess(hotel -> logger.info("Hotel {} saved successfully"));
    }

    private HotelDetails mapToHotel(CheckHotelResponseDTO  hotelResponse,
                                          CreateHotelRoomDTO createHotelRoomDTO){
        return HotelDetails
                .builder()
                .hotelId(hotelResponse.getHotelId())
                .roomTypeId(hotelResponse.getRoomTypeId())
                .roomCount(createHotelRoomDTO.getRoomCount())
                .price(createHotelRoomDTO.getPrice())
                .build();
    }

    private Mono<Object> buildInternalCallRequest(CreateHotelRoomDTO createHotelRoomDTO){  // will chnage return type to CheckHotelREsponseDTO
       CheckHotelRequestDTO request = CheckHotelRequestDTO
               .builder()
               .hotelName(createHotelRoomDTO.getHotelName())
               .roomType(createHotelRoomDTO.getRoomType())
               .price(createHotelRoomDTO.getPrice())
               .build();
return  hotelServiceWebClient.checkHotelAndRoomType(request)
        .flatMap(responseDTO -> {
            // Adjust this line as needed
            responseParser.setResponseBody(responseDTO);
            try {
                Integer id = responseParser.getValue("hotelId", Integer.class);
                Integer c = id;
            } catch (ResponseParser.KeyNotFoundException e) {
                throw new RuntimeException(e);
            }
            return Mono.just(responseDTO);
        });
//       return hotelServiceWebClient.checkHotelAndRoomType(request)
//                .flatMap(responseDTO -> {
//                    // Extract hotelId from the responseDTO
//                    try {
//                        // Extract hotelId from the responseDTO
//                        Map<String, Object> parsedResponse = responseParser.setResponseBody(responseDTO);
//                        Integer hotelId = responseParser.getValue("hotelId", Integer.class);
//                        return Mono.just(responseDTO);
//                    } catch (ResponseParser.KeyNotFoundException e) {
//                        // Handle the case where "hotelId" is not found in the response
//                        // Log the error or handle it appropriately
//                        return Mono.error(e);
//                    }
//
//
//                });

    }
}
