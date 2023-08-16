package io.deeplay.intership.server;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Converter {
    private final ObjectMapper objectMapper;

    public Converter() {
        this.objectMapper = new ObjectMapper();
    }

    public <T> T getClassFromJson(String jsonString, Class<T> classOfT) {
        return null;
    }

    public String objectToJson(Object object) {
        return null;
    }
}
