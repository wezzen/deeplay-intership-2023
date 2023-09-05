package io.deeplay.intership.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TurnDtoResponse extends BaseDtoResponse {
    @JsonCreator
    public TurnDtoResponse(
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("message") String message) {
        super(status, message);
    }
}
