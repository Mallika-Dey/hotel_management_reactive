package com.example.hotelservice.services.impl;

import com.example.hotelservice.dto.request.CreateHotelRequestDTO;
import com.example.hotelservice.dto.request.LocationDto;
import com.example.hotelservice.entity.Hotel;
import com.example.hotelservice.repositories.HotelRepository;
import com.example.hotelservice.services.IHotelService;
import com.example.hotelservice.validator.HotelValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.example.hotelservice.utils.Constants.EMPTY_ROOM;
import static com.example.hotelservice.utils.Constants.ROOM_PRICE_ZERO;

/**
 * Service class for managing Hotel.
 * Author: K M Farhat Snigdah & Mallika Dey
 */
@Service
public class HotelService implements IHotelService {
    private static final Logger logger = LoggerFactory.getLogger(RoomTypeService.class);
    private final HotelRepository hotelRepository;
    private final HotelValidator hotelValidator;

    /**
     * Constructs a HotelService with the provided HotelRepository.
     *
     * @param hotelRepository the repository for room types
     */
    public HotelService(HotelRepository hotelRepository, HotelValidator hotelValidator) {
        this.hotelRepository = hotelRepository;
        this.hotelValidator = hotelValidator;
    }

//    public Mono<String> createHotel(CreateHotelRequestDTO createHotelRequestDTO){
//        return hotelRepository
//                .save(mapToEntity(createHotelRequestDTO))
//                .doOnSuccess(hotel -> logger.info("Location {} saved successfully", hotel))
//                .onErrorMap(throwable -> {
//                    logger.error("Error occurred while creating hotel", throwable);
//                    return new RuntimeException("Failed to create hotel", throwable);
//                })
//                .map(hotel -> "Hotel Created Successfully");
//    }

    @Override
    public Mono<Hotel> createHotel(CreateHotelRequestDTO createHotelRequestDTO) {
        return Mono.just(createHotelRequestDTO)
                .flatMap(hotelRequestDTO -> {
                    hotelValidator.checkByHotelName(hotelRequestDTO.getName());
                    return hotelRepository.save(mapToEntity(hotelRequestDTO));
                })
                .doOnSuccess(hotel -> logger.info("Location {} saved successfully", hotel))
                .doOnError(throwable -> {
                    logger.error("Error occurred while creating hotel", throwable);
                    throw new RuntimeException("Failed to create hotel", throwable);
                });
    }


    private Hotel mapToEntity(CreateHotelRequestDTO createHotelRequestDTO) {
        return Hotel
                .builder()
                .locId(createHotelRequestDTO.getLocId())
                .name(createHotelRequestDTO.getName())
                .amenities(createHotelRequestDTO.getAmenities())
                .availability(EMPTY_ROOM)
                .maxPrice(ROOM_PRICE_ZERO)
                .minPrice(ROOM_PRICE_ZERO)
                .build();
    }
}
