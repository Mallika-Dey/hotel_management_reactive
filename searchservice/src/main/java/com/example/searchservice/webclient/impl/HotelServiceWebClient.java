package com.example.searchservice.webclient.impl;

import com.example.searchservice.webclient.IHotelServiceWebClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Mallika Dey
 */
@Service
public class HotelServiceWebClient implements IHotelServiceWebClient {
    private final WebClient.Builder webclient;

    public HotelServiceWebClient(WebClient.Builder webclient) {
        this.webclient = webclient.baseUrl("http://localhost:8085");
    }

    //public
}
