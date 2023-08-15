package io.deeplay.intership.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.deeplay.intership.dto.Dto;

public class Converter {
    private final ObjectMapper objectMapper;

    public Converter() {
        this.objectMapper = new ObjectMapper();
    }

    public <T> T getClassFromJson(String jsonString, Class<T> classOfT) {
        try {
            return objectMapper.readValue(jsonString, classOfT);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Dto jsonToObject(String string) {
        return null;
    }

    public String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
