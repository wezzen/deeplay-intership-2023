package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public record CreateGameDtoRequest(RequestType requestType, boolean withBot, String color, int size, String token) {

}
