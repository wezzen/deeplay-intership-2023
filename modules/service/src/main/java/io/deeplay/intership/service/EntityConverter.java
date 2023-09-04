package io.deeplay.intership.service;

import io.deeplay.intership.dto.request.TurnDtoRequest;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;

public class EntityConverter {
    public Stone turnDtoToModel(final TurnDtoRequest dtoRequest) {
        return new Stone(
                Color.valueOf(dtoRequest.color),
                dtoRequest.row,
                dtoRequest.column);
    }
}
