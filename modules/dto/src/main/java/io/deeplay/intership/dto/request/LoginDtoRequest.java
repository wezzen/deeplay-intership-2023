package io.deeplay.intership.dto.request;

public record LoginDtoRequest(RequestType requestType, String login, String passwordHash) {

}
