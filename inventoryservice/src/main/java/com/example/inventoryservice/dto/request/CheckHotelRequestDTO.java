package com.example.inventoryservice.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CheckHotelRequestDTO {
    private String hotelName;
    private String roomType;
    private Integer price;
}
