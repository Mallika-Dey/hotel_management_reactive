package com.example.inventoryservice.exception;

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
