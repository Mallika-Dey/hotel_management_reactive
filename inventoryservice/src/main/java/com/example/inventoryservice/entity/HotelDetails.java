package com.example.inventoryservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table("hotel_details")
public class HotelDetails {
    @Id
    private Integer id;

    @Column("hotel_id")
    private Integer hotelId;

    @Column("room_type_id")
    private Integer roomTypeId;

    @Column("room_count")
    private Integer roomCount;

    private Integer price;
}
