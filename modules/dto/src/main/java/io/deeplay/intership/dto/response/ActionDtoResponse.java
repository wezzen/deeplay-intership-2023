package io.deeplay.intership.dto.response;

import io.deeplay.intership.model.Stone;

public class ActionDtoResponse extends BaseDtoResponse {
    public final String status;
    public final String message;
    public final Stone[][] gameField;

    public ActionDtoResponse(String status, String message, Stone[][] gameField) {
        this.status = status;
        this.message = message;
        this.gameField = gameField;
    }

    public ActionDtoResponse() {
        this(null, null, null);
    }
}
