package io.deeplay.intership.dto.response.authorization;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.ResponseStatus;

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
