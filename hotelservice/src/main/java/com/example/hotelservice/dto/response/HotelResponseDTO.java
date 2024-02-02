package com.example.hotelservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponseDTO {
    private String hotelName;
    private String locName;
    private String amenities;
    private Boolean availability;
    private Integer maxPrice;
    private Integer minPrice;
}
