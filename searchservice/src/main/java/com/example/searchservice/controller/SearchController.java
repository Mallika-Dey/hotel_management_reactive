package com.example.searchservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Mallika Dey
 */
@RestController
public class SearchController {
    @GetMapping("/create-room")
    public Mono<ResponseEntity<Object>> createHotelRoom(@RequestParam String location,
                                                        @RequestParam Integer price, @RequestParam String roomTYpe) {
        return null;
    }
}
