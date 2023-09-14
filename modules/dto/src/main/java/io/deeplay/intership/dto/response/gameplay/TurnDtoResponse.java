package io.deeplay.intership.dto.response.gameplay;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.ResponseStatus;

public class TurnDtoResponse extends BaseDtoResponse {
    public final String color;
    public final int row;
    public final int column;
    public final String token;

    @JsonCreator
    public TurnDtoResponse(
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("message") String message,
            @JsonProperty("color") String color,
            @JsonProperty("row") int row,
            @JsonProperty("column") int column,
            @JsonProperty("token") String token) {
        super(status, message);
        this.color = color;
        this.row = row;
        this.column = column;
        this.token = token;
    }
}
