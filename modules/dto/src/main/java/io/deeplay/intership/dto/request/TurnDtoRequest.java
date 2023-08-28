package io.deeplay.intership.dto.request;

public record TurnDtoRequest(RequestType requestType, String color, int row, int column, String token) {

}
