package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public record LoginDtoRequest(RequestType requestType, String login, String passwordHash) {
}