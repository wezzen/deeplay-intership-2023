package io.deeplay.intership.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TurnDtoRequest extends BaseDtoRequest {
    public final String color;
    public final int row;
    public final int column;
    public final String token;

    @JsonCreator
    public TurnDtoRequest(
            @JsonProperty("color") String color,
            @JsonProperty("row") int row,
            @JsonProperty("column") int column,
            @JsonProperty("token") String token) {
        this.color = color;
        this.row = row;
        this.column = column;
        this.token = token;
    }
}
