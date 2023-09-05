package io.deeplay.intership.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.deeplay.intership.model.Stone;

public class StartGameDtoResponse extends BaseDtoResponse {
    public final Stone[][] gameField;

    @JsonCreator
    public StartGameDtoResponse(
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("message") String message,
            @JsonProperty("gameField") Stone[][] gameField) {
        super(status, message);
        this.gameField = gameField;
    }
}
