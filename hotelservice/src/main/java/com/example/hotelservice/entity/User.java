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
@Table("_users")
public class User {
    @Id
    private Integer id;

    private String name;
    private Integer age;
    //private double salary;

}
