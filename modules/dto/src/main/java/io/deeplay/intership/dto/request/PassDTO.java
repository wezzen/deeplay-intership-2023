package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public record PassDTO(RequestType requestType, String token) {
}
