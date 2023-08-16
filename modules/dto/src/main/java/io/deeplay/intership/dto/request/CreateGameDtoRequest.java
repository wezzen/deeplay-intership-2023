package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public class CreateGameDtoRequest extends BaseDto {
    public final boolean withBot;
    public final String color;
    public final int size;
    public final String token;

    public CreateGameDtoRequest(RequestType requestType, boolean withBot, String color, int size, String token) {
        super(requestType);
        this.withBot = withBot;
        this.color = color;
        this.size = size;
        this.token = token;
    }
}
