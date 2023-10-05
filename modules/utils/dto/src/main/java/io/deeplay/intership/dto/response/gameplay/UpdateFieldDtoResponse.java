package io.deeplay.intership.dto.response.gameplay;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.model.Stone;

public class UpdateFieldDtoResponse extends BaseDtoResponse {
    public final Stone[][] gameField;

    @JsonCreator
    public UpdateFieldDtoResponse(
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("message") String message,
            @JsonProperty("gameField") Stone[][] gameField) {
        super(status, message);
        this.gameField = gameField;
    }
}
