package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public record LogOutDTO(RequestType requestType, String token) {
}
