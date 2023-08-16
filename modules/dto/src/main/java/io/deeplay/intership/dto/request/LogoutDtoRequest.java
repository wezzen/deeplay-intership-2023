package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public class LogoutDtoRequest extends BaseDto {
    public final String token;

    public LogoutDtoRequest(RequestType requestType, String token) {
        super(requestType);
        this.token = token;
    }
}
