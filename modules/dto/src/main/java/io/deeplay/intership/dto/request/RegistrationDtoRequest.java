package io.deeplay.intership.dto.request;

public record RegistrationDtoRequest(RequestType requestType, String login, String passwordHash) {

}
