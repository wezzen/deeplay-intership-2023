package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public record RegistrationDTO(RequestType requestType, String login, String passwordHash) {
}
