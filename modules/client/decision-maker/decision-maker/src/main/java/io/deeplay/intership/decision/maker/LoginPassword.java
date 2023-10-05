package io.deeplay.intership.decision.maker;

import io.deeplay.intership.dto.request.RequestType;

public record LoginPassword(RequestType type, String login, String password) {
}
