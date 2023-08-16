package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public record BaseDto(RequestType requestType) {
    public BaseDto {
    }
}
