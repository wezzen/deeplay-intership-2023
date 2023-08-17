package io.deeplay.intership.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

public class JSONConverter {
    public <T> T getClassFromJson(String jsonString, Class<T> classOfT) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonString, classOfT);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String objectToJson(Object object) {
        return new JSONObject(object.toString()).toString();
    }
}

