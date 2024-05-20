package com.example.inventoryservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomBookDTO {

    private String hotelName;

    private String roomType;

    private LocalDate startDate;

    private LocalDate endDate;
}
