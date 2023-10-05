package io.deeplay.intership.dto.request.gameplay;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.deeplay.intership.dto.request.BaseDtoRequest;

public class FinishGameDtoRequest extends BaseDtoRequest {
    public final String gameId;

    @JsonCreator
    public FinishGameDtoRequest(@JsonProperty("gameId") String gameId) {
        this.gameId = gameId;
    }
}
