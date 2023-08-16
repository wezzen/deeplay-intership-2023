package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public class BaseDto {
    public final RequestType requestType;

    public BaseDto(RequestType requestType) {
        this.requestType = requestType;
    }
}
