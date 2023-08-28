package io.deeplay.intership.dto.request;

public record CreateGameDtoRequest(RequestType requestType, boolean withBot, String color, int size, String token) {

}
