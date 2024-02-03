package com.example.hotelservice.validator;

import com.example.hotelservice.entity.RoomType;
import com.example.hotelservice.exception.CustomException;
import com.example.hotelservice.repositories.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RoomTypeValidator {
    private final RoomTypeRepository roomTypeRepository;

    public Mono<RoomType> findByRoomTypeName(String roomType) {
        return roomTypeRepository.findByRoomType(roomType)
                .switchIfEmpty(Mono.error(new CustomException("Room type not exists")));
    }
}
