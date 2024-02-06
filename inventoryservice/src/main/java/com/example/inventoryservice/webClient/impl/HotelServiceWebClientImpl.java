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

@Service
public class HotelServiceWebClientImpl implements HotelServiceWebClient {

    private final WebClient.Builder webclient;

    public HotelServiceWebClientImpl(WebClient.Builder webclient) {
        this.webclient = webclient.baseUrl("http://localhost:8085");
    }

    @Override
    public Mono<CheckHotelResponseDTO> checkHotelAndRoomType(@RequestBody CheckHotelRequestDTO checkHotelRequestDTO){
        return webclient
                .build()
                .post()
                .uri("/api/v2/proxy/get/hotel_name_room_type")
                .bodyValue(checkHotelRequestDTO)
                .retrieve()
                .bodyToMono(CheckHotelResponseDTO.class)
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


    private boolean retryableException(Throwable ex) {
        if (ex instanceof WebClientResponseException responseException) {
            return responseException.getStatusCode().is5xxServerError();
        }
        return ex instanceof WebClientRequestException;
    }
}