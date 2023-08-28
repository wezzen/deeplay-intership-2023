package io.deeplay.intership.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PassDtoRequest extends BaseDtoRequest {
    public final String token;

    @JsonCreator
    public PassDtoRequest(@JsonProperty("token") String token) {
        this.token = token;
    }
}
