package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public class FinishGameDtoRequest extends BaseDto {
    public final String gameId;

    public FinishGameDtoRequest(RequestType requestType, String gameId) {
        super(requestType);
        this.gameId = gameId;
    }
}
