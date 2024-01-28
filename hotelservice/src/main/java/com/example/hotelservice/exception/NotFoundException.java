package com.example.hotelservice.exception;

import lombok.*;

/**
 * @author Mallika Dey
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotFoundException extends RuntimeException{
    private String message;
}
