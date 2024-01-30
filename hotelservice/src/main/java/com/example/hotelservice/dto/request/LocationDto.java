package com.example.hotelservice.dto.request;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private String district;
    private Double latitude;
    private Double longitude;
}
