package com.example.hotelservice.validator;

import com.example.hotelservice.entity.Hotel;
import com.example.hotelservice.exception.CustomException;
import com.example.hotelservice.exception.NotFoundException;
import com.example.hotelservice.repositories.HotelRepository;
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

    public Mono<Void> checkByHotelName(String hotelName) {
        return hotelRepository.existsByName(hotelName)
                .flatMap(
                        exists -> {
                            if (exists) {
                                return Mono.error(new CustomException("Hotel already exists"));
                            } else {
                                return Mono.empty();
                            }
                        }
                );
    }
}
