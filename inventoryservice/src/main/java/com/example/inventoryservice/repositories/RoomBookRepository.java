package com.example.inventoryservice.repositories;

import com.example.inventoryservice.entity.RoomBook;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface RoomBookRepository extends R2dbcRepository<RoomBook, Integer> {
}
