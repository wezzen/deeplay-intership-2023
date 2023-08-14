package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public record JoinGameDTO(RequestType requestType, String gameId, String token) {
}
