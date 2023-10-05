package io.deeplay.intership.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.deeplay.intership.dto.response.authorization.LoginDtoResponse;
import io.deeplay.intership.dto.response.game.CreateGameDtoResponse;
import io.deeplay.intership.dto.response.gameplay.AnswerDtoResponse;
import io.deeplay.intership.dto.response.gameplay.FinishGameDtoResponse;
import io.deeplay.intership.dto.response.gameplay.StartGameDtoResponse;
import io.deeplay.intership.dto.response.gameplay.UpdateFieldDtoResponse;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "responseType",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateGameDtoResponse.class, name = "CREATE_GAME"),
        @JsonSubTypes.Type(value = FailureDtoResponse.class, name = "FAILURE"),
        @JsonSubTypes.Type(value = FinishGameDtoResponse.class, name = "FINISH_GAME"),
        @JsonSubTypes.Type(value = InfoDtoResponse.class, name = "INFO"),
        @JsonSubTypes.Type(value = LoginDtoResponse.class, name = "LOGIN"),
        @JsonSubTypes.Type(value = AnswerDtoResponse.class, name = "ANSWER"),
        @JsonSubTypes.Type(value = StartGameDtoResponse.class, name = "START_GAME"),
        @JsonSubTypes.Type(value = UpdateFieldDtoResponse.class, name = "UPDATE")
})
public class BaseDtoResponse {
    public final ResponseStatus status;
    public final String message;

    @JsonCreator
    public BaseDtoResponse(
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("message") String message) {
        this.status = status;
        this.message = message;
    }
}
