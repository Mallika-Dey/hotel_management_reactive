package com.example.inventoryservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateHotelRoomDTO {

    private String hotelName;

    private String roomType;

    private Integer roomCount;

    private Integer price;
}
