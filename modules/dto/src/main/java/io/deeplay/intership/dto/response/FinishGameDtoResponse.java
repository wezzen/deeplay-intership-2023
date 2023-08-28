package io.deeplay.intership.dto.response;

public class FinishGameDtoResponse extends BaseDtoResponse {
    public final String status;
    public final String message;
    public final int blackScore;
    public final int whiteScore;

    public FinishGameDtoResponse(String status, String message, int blackScore, int whiteScore) {
        this.status = status;
        this.message = message;
        this.blackScore = blackScore;
        this.whiteScore = whiteScore;
    }

    public FinishGameDtoResponse() {
        this(null, null, 0, 0);
    }
}
