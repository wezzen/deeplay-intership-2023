package io.deeplay.intership.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SurrenderDtoRequest extends BaseDtoRequest {
    public final String token;

    @JsonCreator
    public SurrenderDtoRequest(
            @JsonProperty("token") String token) {
        super(RequestType.SURRENDER);
        this.token = token;
    }
}

