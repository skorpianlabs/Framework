package com.and.apiService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseAPI {

    // Common method to convert any object to JSON
    public String convertObjectToJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";  // Return an empty JSON object in case of error
        }
    }
}
