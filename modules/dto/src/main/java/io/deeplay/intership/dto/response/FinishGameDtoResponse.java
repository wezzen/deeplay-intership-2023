package io.deeplay.intership.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FinishGameDtoResponse extends BaseDtoResponse {
    public final int blackScore;
    public final int whiteScore;

    @JsonCreator
    public FinishGameDtoResponse(
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("message") String message,
            @JsonProperty("blackScore") int blackScore,
            @JsonProperty("whiteScore") int whiteScore) {
        super(status, message);
        this.blackScore = blackScore;
        this.whiteScore = whiteScore;
    }
}
