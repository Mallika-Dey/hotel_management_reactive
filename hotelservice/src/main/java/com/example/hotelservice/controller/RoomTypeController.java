package com.example.hotelservice.controller;

import com.example.hotelservice.dto.request.CreateRoomTypeDTO;
import com.example.hotelservice.entity.RoomType;
import com.example.hotelservice.response.ReactiveResponseHandler;
import com.example.hotelservice.services.IRoomTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/room-types")
public class RoomTypeController {

    private final IRoomTypeService roomTypeService;

    /**
     * Constructs a RoomTypeController with the provided IRoomTypeService.
     *
     * @param roomTypeService the room type service interface
     */
    public RoomTypeController(IRoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    /**
     * Endpoint for creating a new room type.
     *
     * @param createRoomTypeDTO the DTO containing information for creating the room type
     * @return a ResponseEntity containing the created room type
     */

    @PostMapping
    public Mono<RoomType> createRoomType(@RequestBody CreateRoomTypeDTO createRoomTypeDTO){
        return roomTypeService.createRoomType(createRoomTypeDTO);
    }

//    @PostMapping
//    public Mono<ResponseEntity<Object>> createRoomType(@RequestBody CreateRoomTypeDTO createRoomTypeDTO){
//        roomTypeService.createRoomType(createRoomTypeDTO);
//        return ReactiveResponseHandler.generateResponse("Room Type Created Successfully",HttpStatus.CREATED);
//    }
}
