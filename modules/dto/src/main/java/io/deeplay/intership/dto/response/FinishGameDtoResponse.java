package io.deeplay.intership.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FinishGameDtoResponse extends BaseDtoResponse {
    public final double blackScore;
    public final double whiteScore;

    @JsonCreator
    public FinishGameDtoResponse(
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("message") String message,
            @JsonProperty("blackScore") double blackScore,
            @JsonProperty("whiteScore") double whiteScore) {
        super(status, message);
        this.blackScore = blackScore;
        this.whiteScore = whiteScore;
    }
}
