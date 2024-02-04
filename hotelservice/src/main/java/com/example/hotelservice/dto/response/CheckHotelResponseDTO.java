package com.example.hotelservice.dto.response;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckHotelResponseDTO {
    private Integer hotelId;
    private Integer roomTypeId;
}
