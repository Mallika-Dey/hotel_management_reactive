package com.example.hotelservice.controller;

import com.example.hotelservice.dto.request.LocationDto;
import com.example.hotelservice.entity.Location;
import com.example.hotelservice.response.ReactiveResponseHandler;
import com.example.hotelservice.services.ILocationService;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {
    private final ILocationService locationService;

    @PostMapping("/create")
    public Mono<ResponseEntity<String>> createLocation(@RequestBody LocationDto locationDto) {
        return locationService.createLocation(locationDto)
                .map(location -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(location));
    }
}
