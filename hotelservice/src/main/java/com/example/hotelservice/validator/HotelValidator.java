package com.example.hotelservice.validator;

import com.example.hotelservice.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Mallika Dey
 */
@Component
@RequiredArgsConstructor
public class HotelValidator {
    private HotelRepository hotelRepository;

//    public Mono<Void> checkByHotelName(String hotelName) {
//        hotelRepository.findByName(hotelName).do()
//    }
}
