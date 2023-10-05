package io.deeplay.intership.dto.request.authorization;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.deeplay.intership.dto.request.BaseDtoRequest;

public class LogoutDtoRequest extends BaseDtoRequest {
    public final String token;

    @JsonCreator
    public LogoutDtoRequest(@JsonProperty("token") String token) {
        this.token = token;
    }
}
