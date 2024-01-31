package com.example.hotelservice.services.impl;

import com.example.hotelservice.dto.request.CreateHotelRequestDTO;
import com.example.hotelservice.dto.request.LocationDto;
import com.example.hotelservice.entity.Hotel;
import com.example.hotelservice.repositories.HotelRepository;
import com.example.hotelservice.services.IHotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import static com.example.hotelservice.utils.Constants.EMPTY_ROOM;
import static com.example.hotelservice.utils.Constants.ROOM_PRICE_ZERO;

/**
 * Service class for managing Hotel.
 * Author: K M Farhat Snigdah & Mallika Dey
 */
public class HotelService implements IHotelService {
    private static final Logger logger = LoggerFactory.getLogger(RoomTypeService.class);
    private final HotelRepository hotelRepository;

    /**
     * Constructs a HotelService with the provided HotelRepository.
     *
     * @param hotelRepository the repository for room types
     */
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
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

    public Mono<Hotel> createHotel(CreateHotelRequestDTO createHotelRequestDTO){
        return Mono.just(createHotelRequestDTO)
                .map(this::mapToEntity)
                .flatMap(hotelRepository::save)
                .doOnSuccess(hotel -> logger.info("Location {} saved successfully", hotel))
                .doOnError(throwable -> {
                    logger.error("Error occurred while creating hotel", throwable);
                    throw new RuntimeException("Failed to create hotel", throwable);
                });
    }


    private Hotel mapToEntity(CreateHotelRequestDTO createHotelRequestDTO){
        return Hotel
                .builder()
                .locId(createHotelRequestDTO.getLocId())
                .amenities(createHotelRequestDTO.getAmenities())
                .availability(EMPTY_ROOM)
                .maxPrice(ROOM_PRICE_ZERO)
                .minPrice(ROOM_PRICE_ZERO)
                .build();
    }
}
