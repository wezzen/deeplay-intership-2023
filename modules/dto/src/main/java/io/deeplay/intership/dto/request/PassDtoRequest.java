package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public class PassDtoRequest extends BaseDto{
    public final String token;

    public PassDtoRequest(RequestType requestType, String token) {
        super(requestType);
        this.token = token;
    }
}
