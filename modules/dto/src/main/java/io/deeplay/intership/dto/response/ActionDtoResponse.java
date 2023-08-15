package io.deeplay.intership.dto.response;

import io.deeplay.intership.model.Stone;

public record ActionDtoResponse(String message, String status, Stone[][] gameField) {
}
