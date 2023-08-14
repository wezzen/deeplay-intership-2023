package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public record LogInDTO(RequestType requestType, String login, String passwordHash) {
}
