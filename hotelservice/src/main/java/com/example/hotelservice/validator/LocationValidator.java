package com.example.hotelservice.validator;

import com.example.hotelservice.entity.Location;
import com.example.hotelservice.exception.CustomException;
import com.example.hotelservice.repositories.LocationRepository;
import com.example.hotelservice.services.impl.RoomTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class LocationValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomTypeService.class);
    private final LocationRepository locationRepository;

    public LocationValidator(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Mono<Void> checkByLocationDistrict(String district) {
        return locationRepository.existsByDistrict(district)
                .flatMap(
                        exists -> {
                            if (exists) {
                                return Mono.error(new CustomException("Location already exists"));
                            }
                            return Mono.empty();
                        }
                );
    }

    public Mono<Location> checkLocationExists(String district) {
        return locationRepository.findByDistrict(district)
                .switchIfEmpty(Mono.error(new CustomException("Location not exists")));
    }
}
