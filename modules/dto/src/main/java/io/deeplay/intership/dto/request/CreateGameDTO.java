package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public record CreateGameDTO(RequestType requestType, Boolean withBot, String color, Integer size, String token) {
}
