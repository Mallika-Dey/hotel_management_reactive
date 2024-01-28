package com.example.hotelservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table("hotel")
public class Hotel {
    @Id
    private Integer id;
    private Integer loc_id;
    private String amenities;
    private Boolean availability;
    private Integer max_price;
    private Integer min_price;
}
