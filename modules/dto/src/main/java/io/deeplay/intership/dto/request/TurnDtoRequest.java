package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public class TurnDtoRequest extends BaseDto{
    public final String color;
    public final int row;
    public final int column;
    public final String token;

    public TurnDtoRequest(RequestType requestType, String color, int row, int column, String token) {
        super(requestType);
        this.color = color;
        this.row = row;
        this.column = column;
        this.token = token;
    }
}
