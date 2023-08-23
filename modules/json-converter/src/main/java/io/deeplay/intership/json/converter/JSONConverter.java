package io.deeplay.intership.json.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONConverter {
    private final ObjectMapper mapper;

    public JSONConverter(final ObjectMapper objectMapper) {
        this.mapper = objectMapper;
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public JSONConverter() {
        this(new ObjectMapper());
    }

    public <T> T getObjectFromJson(String jsonString, Class<T> classOfT) {
        try {
            return mapper.readValue(jsonString, classOfT);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getJsonFromObject(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

