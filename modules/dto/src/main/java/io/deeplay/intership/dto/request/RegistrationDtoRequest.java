package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public record RegistrationDtoRequest(RequestType requestType, String login, String passwordHash) {

}
