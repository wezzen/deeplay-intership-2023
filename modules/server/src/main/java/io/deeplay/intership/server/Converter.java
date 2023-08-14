package io.deeplay.intership.server;

public class Converter {
    public String objectToJson(Dto dto, Class clazz) {

        return dto.toString();
    }

    public Dto JsonToObject(String string) {
        return null;
    }
}
