package io.deeplay.intership.dto.request;

public record JoinGameDtoRequest(RequestType requestType, String gameId, String token, String color) {

}
