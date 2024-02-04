package com.example.inventoryservice.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ReactiveResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.generateGenericResponse(message, status, responseObj);

        return ResponseEntity.status(status).body(genericResponse.response);
    }

    // Overloaded method for cases where data is not needed Like Delete etc.
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status) {
        return generateResponse(message, status, null);
    }
}
