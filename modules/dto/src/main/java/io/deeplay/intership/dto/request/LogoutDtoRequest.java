package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public record LogoutDtoRequest(RequestType requestType, String token) {

}
