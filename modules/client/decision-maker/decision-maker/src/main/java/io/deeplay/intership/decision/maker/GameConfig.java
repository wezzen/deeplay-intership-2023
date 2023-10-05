package io.deeplay.intership.decision.maker;

import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.model.Color;

public record GameConfig(RequestType type, boolean withBot, Color color, int size, String gameId) {
}
