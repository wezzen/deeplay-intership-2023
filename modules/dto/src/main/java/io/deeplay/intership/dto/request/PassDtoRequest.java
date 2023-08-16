package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public record PassDtoRequest(RequestType requestType, String token) {

}
