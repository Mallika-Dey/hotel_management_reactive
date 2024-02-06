package com.example.hotelservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table("hotel")
public class Hotel {
    @Id
    private Integer id;
    private Integer locId;
    private String name;
    private String amenities;
    private Boolean availability;
    private Integer maxPrice;
    private Integer minPrice;
}
