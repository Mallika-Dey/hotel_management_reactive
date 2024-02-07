package com.example.inventoryservice.webClient.impl;

import com.example.inventoryservice.dto.request.CheckHotelRequestDTO;
import com.example.inventoryservice.dto.response.CheckHotelResponseDTO;
import com.example.inventoryservice.exception.CustomException;
import com.example.inventoryservice.webClient.HotelServiceWebClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class HotelServiceWebClientImpl implements HotelServiceWebClient {

    private final WebClient.Builder webclient;

    public HotelServiceWebClientImpl(WebClient.Builder webclient) {
        this.webclient = webclient.baseUrl("http://localhost:8085");
    }

    @Override
    public Mono<String> checkHotelAndRoomType(@RequestBody CheckHotelRequestDTO checkHotelRequestDTO){
        return webclient
                .build()
                .post()
                .uri("/api/v2/proxy/get/hotel_name_room_type")
                .bodyValue(checkHotelRequestDTO)
                .retrieve()
                .bodyToMono(String.class)
               // .map(this::parseResponse)
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(20))
                        .filter(this::retryableException))
                .onErrorResume(ex -> {
                    if (ex instanceof WebClientResponseException) {
                        WebClientResponseException webClientResponseException = (WebClientResponseException) ex;
                        String errorMessage = webClientResponseException.getResponseBodyAsString();
                        throw new CustomException(errorMessage);
                    } else {
                        throw new CustomException("Unknown error occurred");
                    }
                });
    }

    private Map<String, Object> parseResponse(String responseBody) {
        // Implement your parsing logic here
        // For simplicity, I'm just returning a map with a sample key and value
        Map<String, Object> parsedData = new HashMap<>();
        parsedData.put("sampleKey", responseBody);
        return parsedData;
    }

    private boolean retryableException(Throwable ex) {
        if (ex instanceof WebClientResponseException responseException) {
            return responseException.getStatusCode().is5xxServerError();
        }
        return ex instanceof WebClientRequestException;
    }
}
