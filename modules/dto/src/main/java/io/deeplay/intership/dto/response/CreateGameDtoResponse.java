package io.deeplay.intership.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateGameDtoResponse extends BaseDtoResponse {
    public final String gameId;

    @JsonCreator
    public CreateGameDtoResponse(
            @JsonProperty("status") String status,
            @JsonProperty("message") String message,
            @JsonProperty("gameId") String gameId) {
        super(status, message);
        this.gameId = gameId;
    }
}
