package io.deeplay.intership.dto.response.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.ResponseStatus;

public class CreateGameDtoResponse extends BaseDtoResponse {
    public final String gameId;

    @JsonCreator
    public CreateGameDtoResponse(
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("message") String message,
            @JsonProperty("gameId") String gameId) {
        super(status, message);
        this.gameId = gameId;
    }
}
