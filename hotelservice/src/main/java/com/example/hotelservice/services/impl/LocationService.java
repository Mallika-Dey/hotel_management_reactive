package com.example.hotelservice.services.impl;

import com.example.hotelservice.dto.request.LocationDto;
import com.example.hotelservice.entity.Location;
import com.example.hotelservice.repositories.LocationRepository;
import com.example.hotelservice.services.ILocationService;
import com.example.hotelservice.validator.LocationValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LocationService implements ILocationService {
    private final Logger logger = LoggerFactory.getLogger("LocationService.class");
    private final LocationRepository locationRepository;
    private final LocationValidator locationValidator;

    @Override
    public Mono<Location> createLocation(LocationDto locationDto) {
        return locationValidator.checkByLocationDistrict(locationDto.getDistrict())
                .then(Mono.just(locationDto))
                .flatMap(locationDto1 -> locationRepository.save(mapToLocation(locationDto1)))
                .doOnSuccess(location -> logger.info("Location {} saved successfully", location.getDistrict()));
//                .onErrorMap(throwable -> {
//                    logger.error("Error ", throwable);
//                    throw new RuntimeException("Failed to create location", throwable);
//                });
    }

    private Location mapToLocation(LocationDto locationDto) {
        return Location
                .builder()
                .district(locationDto.getDistrict())
                .latitude(locationDto.getLatitude())
                .longitude(locationDto.getLongitude())
                .build();
    }
}
