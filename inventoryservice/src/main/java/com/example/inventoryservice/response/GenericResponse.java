package com.example.inventoryservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static com.example.inventoryservice.utils.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse {
    Map<String, Object> response = new HashMap<>();
    Map<String, Object> meta = new HashMap<>();

    public void generateGenericResponse(String message, HttpStatus status, Object responseObj) {
        this.meta.put(STATUS_KEY, status);
        this.meta.put("code", status.value());
        this.meta.put(MESSAGE_KEY, message);
        this.response.put("meta", meta);

        if (responseObj != null) {
            this.response.put(DATA_KEY, responseObj);
        }
    }
}
