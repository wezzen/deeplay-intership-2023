package io.deeplay.intership.json.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONConverter {
    private final ObjectMapper mapper;

    public JSONConverter(){
        mapper = new ObjectMapper();
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

