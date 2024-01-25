package com.example.inventoryservice.repositories;

import com.example.inventoryservice.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends R2dbcRepository<User, Integer> {
    Flux<User> findByAge(Integer age);
}
