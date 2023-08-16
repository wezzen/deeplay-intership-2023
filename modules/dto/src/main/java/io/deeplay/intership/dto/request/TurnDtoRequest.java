package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public record TurnDtoRequest(RequestType requestType, String color, int row, int column, String token) {

}
