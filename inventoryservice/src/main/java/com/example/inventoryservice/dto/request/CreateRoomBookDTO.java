package com.example.inventoryservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomBookDTO {

    private String hotelName;

    private String roomType;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
