package com.example.hotelservice.services.impl;

import com.example.hotelservice.dto.request.CreateRoomTypeDTO;
import com.example.hotelservice.entity.RoomType;
import com.example.hotelservice.repositories.RoomTypeRepository;
import com.example.hotelservice.services.IRoomTypeService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * Service class for managing room types.
 * Author: K M Farhat Snigdah
 */
@Service
public class RoomTypeService implements IRoomTypeService {
    private static final Logger logger = LoggerFactory.getLogger(RoomTypeService.class);
    private final RoomTypeRepository roomTypeRepository;

    /**
     * Constructs a RoomTypeService with the provided RoomTypeRepository.
     *
     * @param roomTypeRepository the repository for room types
     */
    public RoomTypeService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }


    /**
     * Creates a new room type based on the provided DTO.
     *
     * @param createRoomTypeDTO the DTO containing information for creating the room type
     * @return a Mono emitting the created RoomType
     */
    public Mono<RoomType> createRoomType(CreateRoomTypeDTO createRoomTypeDTO){
        RoomType createRoom = RoomType
                .builder()
                .roomType(createRoomTypeDTO.getRoomType())
                .build();

        // Save the new RoomType in the repository
        return roomTypeRepository.save(createRoom)
                .doOnSuccess(roomType -> logger.info("Create Room Successfully"))
                .onErrorMap(throwable -> {
                    logger.error("Error while creating room");
                    return new RuntimeException("Fail to create student");
                });
    }
}
