package io.deeplay.intership.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LogoutDtoRequest extends BaseDtoRequest {
    public final String token;

    @JsonCreator
    public LogoutDtoRequest(@JsonProperty("token") String token) {
        super(RequestType.LOGOUT);
        this.token = token;
    }
}
