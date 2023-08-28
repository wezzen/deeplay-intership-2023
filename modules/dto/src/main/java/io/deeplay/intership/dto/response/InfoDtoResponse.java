package io.deeplay.intership.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InfoDtoResponse extends BaseDtoResponse {
    @JsonCreator
    public InfoDtoResponse(
            @JsonProperty("status") String status,
            @JsonProperty("message") String message) {
        super(status, message);
    }
}
