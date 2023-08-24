package io.deeplay.intership.decision.maker;

import io.deeplay.intership.dto.RequestType;

public record LoginPassword(RequestType type, String login, String password) {
}
