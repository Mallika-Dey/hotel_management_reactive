package com.example.searchservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mallika Dey
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotFoundException extends RuntimeException{
    private String message;
}
