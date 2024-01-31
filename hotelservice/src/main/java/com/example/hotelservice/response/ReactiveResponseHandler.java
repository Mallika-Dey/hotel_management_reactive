package com.example.hotelservice.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static com.example.hotelservice.utils.Constants.*;

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
