package com.example.inventoryservice.parser;

import org.springframework.stereotype.Component;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
@Component
public class ResponseParser {
    private  JSONObject jsonResponse;

    // Updated constructor to remove the String parameter
    public ResponseParser() {
        // Initialize jsonResponse to an empty JSONObject
        this.jsonResponse = new JSONObject();
    }

    // Setter method for responseBody
    public void setResponseBody(String responseBody) {
        this.jsonResponse = new JSONObject(responseBody);
    }


    public <T> T getValue(String key, Class<T> type) throws KeyNotFoundException {
        if (jsonResponse.has(key)) {
            return convertValue(jsonResponse.get(key), type);
        } else {
            throw new KeyNotFoundException("Key '" + key + "' not found in the response.");
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T convertValue(Object value, Class<T> type) throws IllegalArgumentException {
        if (type == String.class) {
            return (T) value.toString();
        } else if (type == Integer.class || type == int.class) {
            return (T) Integer.valueOf(value.toString());
        } else if (type == Double.class || type == double.class) {
            return (T) Double.valueOf(value.toString());
        } else if (type == Boolean.class || type == boolean.class) {
            return (T) Boolean.valueOf(value.toString());
        } else if (type == JSONObject.class) {
            return (T) value;
        } else if (type == JSONArray.class) {
            return (T) value;
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }

    public static class KeyNotFoundException extends Exception {
        public KeyNotFoundException(String message) {
            super(message);
        }
    }
}
