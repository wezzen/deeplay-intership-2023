package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public record CreateGameDtoRequest(RequestType requestType, Boolean withBot, String color, int size, String token) {
}
