package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public class SurrenderDtoRequest extends BaseDto {
    public final String token;

    public SurrenderDtoRequest(RequestType requestType, String token) {
        super(requestType);
        this.token = token;
    }
}
