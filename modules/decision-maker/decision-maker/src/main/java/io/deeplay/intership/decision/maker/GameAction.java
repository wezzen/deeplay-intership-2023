package io.deeplay.intership.decision.maker;

import io.deeplay.intership.dto.request.RequestType;

public record GameAction(RequestType type, int row, int column) {
}
