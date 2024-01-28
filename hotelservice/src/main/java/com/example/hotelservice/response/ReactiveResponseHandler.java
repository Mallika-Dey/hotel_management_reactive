package com.example.hotelservice.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static com.example.hotelservice.utils.Constants.*;

public class ReactiveResponseHandler {
    public static Mono<ServerResponse> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> meta = new HashMap<>();

        meta.put(STATUS_KEY, status);
        meta.put("code", status.value());
        meta.put(MESSAGE_KEY, message);

        response.put("meta", meta);

        if(responseObj != null){
            response.put(DATA_KEY, responseObj);
        }

        return ServerResponse.status(status)
                .body(BodyInserters.fromValue(response));
    }

    // Overloaded method for cases where data is not needed Like Delete etc.
    public static Mono<ServerResponse> generateResponse(String message, HttpStatus status) {
        return generateResponse(message, status, null);
    }
}
