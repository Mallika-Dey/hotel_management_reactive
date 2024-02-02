package com.example.hotelservice.validator;

import com.example.hotelservice.dto.request.CreateHotelRequestDTO;
import com.example.hotelservice.dto.request.LocationDto;
import com.example.hotelservice.entity.Hotel;
import com.example.hotelservice.entity.Location;
import com.example.hotelservice.exception.CustomException;
import com.example.hotelservice.repositories.HotelRepository;
import com.example.hotelservice.services.impl.RoomTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Mallika Dey
 */
@Component
public class HotelValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomTypeService.class);
    private final HotelRepository hotelRepository;
    private final LocationValidator locationValidator;

    public HotelValidator(HotelRepository hotelRepository, LocationValidator locationValidator) {
        this.hotelRepository = hotelRepository;
        this.locationValidator = locationValidator;
    }

    public Mono<Location> hotelCreateValidation(CreateHotelRequestDTO requestDTO) {
        return checkByHotelName(requestDTO.getName())
                .then(locationValidator.checkLocationExists(requestDTO.getLocationName()));
    }

    public Mono<Hotel> hotelResponseValidation(String hotelName) {
        return findByHotelName(hotelName);

    }

    public Mono<Void> checkByHotelName(String hotelName) {
        return hotelRepository.existsByName(hotelName)
                .flatMap(
                        exists -> {
                            if (exists) {
                                LOGGER.error("HOTEL already exists");
                                return Mono.error(new CustomException("Hotel already exists"));
                            }
                            return Mono.empty();
                        }
                );
    }

    public Mono<Hotel> findByHotelName(String hotelName) {
        return hotelRepository.findByName(hotelName)
                .switchIfEmpty(Mono.error(new CustomException("Hotel not exists")));
    }


//    public Mono<Location> hotelCreateValidation(CreateHotelRequestDTO requestDTO) {
//
////            Mono<Void> hotelNameValidation = checkByHotelName(requestDTO.getName()).then();
////            Mono<Location> locationValidation = locationValidator.checkLocationExists(requestDTO.getLocationName());
////
////            return Mono.zip(hotelNameValidation, locationValidation)
////                    .then(locationValidation);
//
//        return checkByHotelName(requestDTO.getName())
//                .then(locationValidator.checkLocationExists(requestDTO.getLocationName()));
//    }
}