package com.example.inventoryservice.parser;

import org.springframework.stereotype.Component;

import org.json.JSONArray;
import org.json.JSONObject;

@Component
public class ResponseParser {
    // Updated constructor to remove the String parameter
    public ResponseParser() {
    }

    // Static method for parsing values
    public static <T> T parseValue(String responseBody, String key, Class<T> type) {
        JSONObject jsonResponse = new JSONObject(responseBody);
        if (jsonResponse.has(key)) {
            return convertValue(jsonResponse.get(key), type);
        } else {
            throw new KeyNotFoundException("Key '" + key + "' not found in the response.");
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T convertValue(Object value, Class<T> type) throws IllegalArgumentException {
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

    public static class KeyNotFoundException extends RuntimeException {
        public KeyNotFoundException(String message) {
            super(message);
        }
    }
}