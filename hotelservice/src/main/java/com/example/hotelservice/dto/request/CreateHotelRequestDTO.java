package com.example.hotelservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateHotelRequestDTO {
    private String locationName;
    private String name;
    private String amenities;
}
