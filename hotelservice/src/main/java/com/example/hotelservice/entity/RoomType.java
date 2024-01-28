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
@Table("room_type")
public class RoomType {
    @Id
    private Long id;
    private Integer loc_id;
    private String amenities;
    private Integer availability;
    private Integer max_price;
    private Integer min_price;
}
