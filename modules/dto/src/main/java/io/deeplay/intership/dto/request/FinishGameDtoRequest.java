package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public record FinishGameDtoRequest(RequestType requestType, String gameId) {

}
