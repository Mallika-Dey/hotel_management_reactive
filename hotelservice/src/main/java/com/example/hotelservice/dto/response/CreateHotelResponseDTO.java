package com.example.hotelservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateHotelResponseDTO {
    private Integer id;
    private Integer locId;
    private String amenities;
    private Boolean availability;
    private Integer maxPrice;
    private Integer minPrice;
}
