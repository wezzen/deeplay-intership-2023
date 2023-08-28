package io.deeplay.intership.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FinishGameDtoRequest extends BaseDtoRequest {
    public final String gameId;

    @JsonCreator
    public FinishGameDtoRequest(@JsonProperty("gameId") String gameId) {
        this.gameId = gameId;
    }
}
