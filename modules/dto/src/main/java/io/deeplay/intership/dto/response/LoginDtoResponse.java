package io.deeplay.intership.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginDtoResponse extends BaseDtoResponse {
    public final String token;

    @JsonCreator
    public LoginDtoResponse(
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("message") String message,
            @JsonProperty("token") String token) {
        super(status, message);
        this.token = token;
    }
}
