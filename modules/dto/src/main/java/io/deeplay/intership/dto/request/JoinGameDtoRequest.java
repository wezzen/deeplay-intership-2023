package io.deeplay.intership.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JoinGameDtoRequest extends BaseDtoRequest {
    public final String gameId;
    public final String token;
    public final String color;

    @JsonCreator
    public JoinGameDtoRequest(
            @JsonProperty("gameId") String gameId,
            @JsonProperty("token") String token,
            @JsonProperty("color") String color) {
        this.gameId = gameId;
        this.token = token;
        this.color = color;
    }
}
