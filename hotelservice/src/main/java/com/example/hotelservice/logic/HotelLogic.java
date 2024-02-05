package com.example.hotelservice.logic;

import com.example.hotelservice.dto.request.CheckHotelRequestDTO;
import com.example.hotelservice.entity.Hotel;
import com.example.hotelservice.repositories.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author KM Snigdah & Mallika Dey
 */
@Component
public class HotelLogic {
    private final Logger logger = LoggerFactory.getLogger("HotelLogic.class");
    private final HotelRepository hotelRepository;

    public HotelLogic(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Mono<Hotel> updateHotelPriceAndAvailability(
            Hotel hotel, CheckHotelRequestDTO checkHotelRequestDTO) {
        return Mono.just(updateToHotel(hotel, checkHotelRequestDTO))
                .flatMap(hotelRepository::save)
                .doOnSuccess(hotel1 -> logger.info("Hotel updated successfully"))
                .onErrorMap(throwable -> {
                    logger.error("Error ", throwable);
                    throw new RuntimeException("Failed to update Hotel", throwable);
                });
    }

    private Hotel updateToHotel(Hotel hotel, CheckHotelRequestDTO requestDto) {
        hotel.setAvailability(true);
        hotel.setMaxPrice(Math.max(hotel.getMaxPrice(), requestDto.getPrice()));
        hotel.setMinPrice(Math.min(hotel.getMinPrice(), requestDto.getPrice()));
        return hotel;
    }


}
