package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public record TurnDTO(RequestType requestType, String color, Integer row, Integer column, String token) {
}
