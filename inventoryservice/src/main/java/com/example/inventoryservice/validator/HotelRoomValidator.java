package com.example.inventoryservice.validator;

import com.example.inventoryservice.entity.HotelDetails;
import com.example.inventoryservice.exception.CustomException;
import com.example.inventoryservice.repositories.HotelDetailsRepository;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Mallika Dey
 */
@Component
public class HotelRoomValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelRoomValidator.class);
    private final HotelDetailsRepository hotelDetailsRepository;

    public HotelRoomValidator(HotelDetailsRepository hotelDetailsRepository) {
        this.hotelDetailsRepository = hotelDetailsRepository;
    }

    public Mono<Boolean> validateHotelByRoomType(Integer hotelId, Integer roomTypeId) {
        return hotelDetailsRepository
                .existsByHotelIdAndRoomTypeId(hotelId, roomTypeId)
                .flatMap(result -> {
                    if (result) {
                        LOGGER.error("Hotel room type already exists");
                        return Mono.error(new CustomException("Hotel room type already exists"));
                    }
                    return Mono.just(false);
                });
    }

    public Mono<HotelDetails> isHotelRoomExists(Integer hotelId, Integer roomTypeId) {
        return hotelDetailsRepository
                .findByHotelIdAndRoomTypeId(hotelId, roomTypeId)
                .flatMap(hotelDetails -> {
                    if (hotelDetails != null) {
                        //return Mono.just(hotelDetails.getHotelId());
                        return Mono.just(hotelDetails);
                    } else {
                        LOGGER.error("Hotel room not exists");
                        return Mono.error(new CustomException("Hotel room not exists"));
                    }
                });
    }

}
