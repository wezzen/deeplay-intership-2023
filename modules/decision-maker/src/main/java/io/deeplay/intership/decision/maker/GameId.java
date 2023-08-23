package io.deeplay.intership.decision.maker;

import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.model.Color;

public record GameId(RequestType type, boolean withBot, Color color, int size, int gameId) {
}
