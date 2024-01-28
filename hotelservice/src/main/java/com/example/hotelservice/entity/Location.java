package com.example.hotelservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    private Long id;
    private String district;
    private Integer availability;
    private Double latitude;
    private Double longitude;
}
