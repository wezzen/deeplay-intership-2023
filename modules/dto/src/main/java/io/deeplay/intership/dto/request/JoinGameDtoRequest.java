package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public class JoinGameDtoRequest extends BaseDto {
    public final String gameId;
    public final String token;

    public JoinGameDtoRequest(RequestType requestType, String gameId, String token) {
        super(requestType);
        this.gameId = gameId;
        this.token = token;
    }
}
