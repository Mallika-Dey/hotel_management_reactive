package com.example.inventoryservice.repositories;

import com.example.inventoryservice.entity.RoomBook;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface RoomBookRepository extends R2dbcRepository<RoomBook, Integer> {
    @Query("SELECT * FROM room_booked " +
            "WHERE hotel_details_id = :hotelDetailsId " +
            "AND (:userStartDate BETWEEN start_date AND end_date " +
            "OR :userEndDate BETWEEN start_date AND end_date)")
    Flux<RoomBook> findAvailableRoomBooks(Integer hotelDetailsId, LocalDate userStartDate, LocalDate userEndDate);
}
