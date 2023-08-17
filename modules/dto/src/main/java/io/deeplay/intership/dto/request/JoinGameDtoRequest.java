package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public record JoinGameDtoRequest(RequestType requestType, String gameId, String token, String color) {

}
