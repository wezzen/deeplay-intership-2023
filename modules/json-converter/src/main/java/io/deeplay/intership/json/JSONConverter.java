package io.deeplay.intership.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONConverter {
    public <T> T getObjectFromJson(String jsonString, Class<T> classOfT) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonString, classOfT);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getJsonFromObject(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

