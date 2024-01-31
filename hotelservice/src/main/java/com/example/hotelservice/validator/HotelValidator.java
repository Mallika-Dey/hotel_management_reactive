package com.example.hotelservice.validator;

import com.example.hotelservice.exception.CustomException;
import com.example.hotelservice.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.flogger.Flogger;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Mallika Dey
 */
@Component
public class HotelValidator {
    private final HotelRepository hotelRepository;

    public HotelValidator(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public void checkByHotelName(String hotelName) {
        Mono<Boolean> res = hotelRepository.existsByName(hotelName);
        System.out.println(res);
    }
}
