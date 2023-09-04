package io.deeplay.intership.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.deeplay.intership.model.Stone;

public class UpdateFieldDtoRequest extends BaseDtoResponse {
    public final Stone[][] gameField;
    public final String currentTurn;

    @JsonCreator
    public UpdateFieldDtoRequest(
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("message") String message,
            @JsonProperty("gameField") Stone[][] gameField,
            @JsonProperty("currentTurn") String currentTurn) {
        super(status, message);
        this.gameField = gameField;
        this.currentTurn = currentTurn;
    }
}
