package com.example.hotelservice.services;

import com.example.hotelservice.dto.request.LocationDto;
import com.example.hotelservice.entity.Location;
import reactor.core.publisher.Mono;

public interface ILocationService {
    public Mono<String> createLocation(LocationDto locationDto);
}
