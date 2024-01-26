package com.example.hotelservice.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mallika Dey
 */
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorMap = new HashMap<>();
        Throwable error = getError(request);
        errorMap.put("error", error.getMessage());
        errorMap.put("path", request.path());
        return errorMap;
    }
}
