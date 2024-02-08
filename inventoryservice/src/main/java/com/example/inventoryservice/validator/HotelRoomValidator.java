package com.example.inventoryservice.validator;

import com.example.inventoryservice.exception.CustomException;
import com.example.inventoryservice.repositories.HotelDetailsRepository;
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

    public Mono<Boolean> validateHotelByRoomType(Integer hotelId, Integer roomId) {
        return hotelDetailsRepository
                .existsByHotelIdAndRoomTypeId(hotelId, roomId)
                .flatMap(result -> {
                    if (result) {
                        System.out.println(result);
                        System.out.println(hotelId + "  " + roomId);
                        LOGGER.error("Hotel room type already exists");
                        return Mono.error(new CustomException("Hotel room type already exists"));
                    }
                    return Mono.just(false);
                });
    }
}
