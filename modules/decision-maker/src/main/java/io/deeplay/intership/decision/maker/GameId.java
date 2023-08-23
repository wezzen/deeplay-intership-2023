package io.deeplay.intership.decision.maker;

import io.deeplay.intership.dto.RequestType;

public record GameId(RequestType type, int gameId) {
}
