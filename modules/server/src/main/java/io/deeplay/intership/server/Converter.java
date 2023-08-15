package io.deeplay.intership.server;

import io.deeplay.intership.dto.Dto;

public class Converter {
    public String objectToJson(Dto dto, Class clazz) {

        return dto.toString();
    }

    public Dto JsonToObject(String string) {
        return null;
    }
}
