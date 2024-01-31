package com.example.hotelservice.services.impl;

import com.example.hotelservice.dto.request.LocationDto;
import com.example.hotelservice.entity.Location;
import com.example.hotelservice.repositories.LocationRepository;
import com.example.hotelservice.services.ILocationService;
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

    @Override
    public Mono<Location> createLocation(LocationDto locationDto) {
        return Mono.just(locationDto)
                .flatMap(locationDto1 -> locationRepository.save(mapToLocation(locationDto1)))
                .doOnSuccess(location -> logger.info("Location %s saved successfully"))
                .onErrorMap(throwable -> {
                    logger.error("Error ", throwable);
                    return new RuntimeException("Failed to create location", throwable);
                });
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
