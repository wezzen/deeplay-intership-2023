package io.deeplay.intership.dto.response;

public class CreateGameDtoResponse extends BaseDtoResponse {
    public final String status;
    public final String message;
    public final String gameId;

    public CreateGameDtoResponse(String status, String message, String gameId) {
        this.status = status;
        this.message = message;
        this.gameId = gameId;
    }

    public CreateGameDtoResponse() {
        this(null, null, null);
    }
}
