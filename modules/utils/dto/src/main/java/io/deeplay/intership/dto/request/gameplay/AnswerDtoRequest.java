package io.deeplay.intership.dto.request.gameplay;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.deeplay.intership.dto.request.BaseDtoRequest;

public class AnswerDtoRequest extends BaseDtoRequest {
    public final AnswerDtoType answerType;
    public final int row;
    public final int column;


    @JsonCreator
    public AnswerDtoRequest(
            @JsonProperty("answerType") AnswerDtoType answerType,
            @JsonProperty("row") int row,
            @JsonProperty("column") int column) {
        this.answerType = answerType;
        this.row = row;
        this.column = column;
    }
}
