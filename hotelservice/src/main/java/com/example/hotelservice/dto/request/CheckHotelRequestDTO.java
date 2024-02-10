package com.example.hotelservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckHotelRequestDTO {
    private String hotelName;
    private String roomType;
}
