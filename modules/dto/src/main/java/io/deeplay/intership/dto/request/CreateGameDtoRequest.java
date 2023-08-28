package io.deeplay.intership.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateGameDtoRequest extends BaseDtoRequest {
    public final boolean withBot;
    public final String color;
    public final int size;
    public final String token;

    @JsonCreator
    public CreateGameDtoRequest(
            @JsonProperty("withBot") boolean withBot,
            @JsonProperty("color") String color,
            @JsonProperty("size") int size,
            @JsonProperty("token") String token) {
        this.withBot = withBot;
        this.color = color;
        this.size = size;
        this.token = token;
    }
}