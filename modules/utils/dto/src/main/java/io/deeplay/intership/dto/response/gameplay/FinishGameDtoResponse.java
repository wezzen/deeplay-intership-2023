package io.deeplay.intership.dto.response.gameplay;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.ResponseStatus;

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
