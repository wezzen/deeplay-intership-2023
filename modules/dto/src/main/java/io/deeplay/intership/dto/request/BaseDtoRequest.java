package io.deeplay.intership.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.deeplay.intership.dto.request.authorization.LoginDtoRequest;
import io.deeplay.intership.dto.request.authorization.LogoutDtoRequest;
import io.deeplay.intership.dto.request.authorization.RegistrationDtoRequest;
import io.deeplay.intership.dto.request.game.CreateGameDtoRequest;
import io.deeplay.intership.dto.request.game.JoinGameDtoRequest;
import io.deeplay.intership.dto.request.gameplay.AnswerDtoRequest;
import io.deeplay.intership.dto.request.gameplay.FinishGameDtoRequest;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "requestType",
        visible = true)
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = CreateGameDtoRequest.class, name = "CREATE_GAME"),
        @JsonSubTypes.Type(value = FinishGameDtoRequest.class, name = "FINISH_GAME"),
        @JsonSubTypes.Type(value = JoinGameDtoRequest.class, name = "JOIN_GAME"),
        @JsonSubTypes.Type(value = LoginDtoRequest.class, name = "LOGIN"),
        @JsonSubTypes.Type(value = LogoutDtoRequest.class, name = "LOGOUT"),
        @JsonSubTypes.Type(value = PassDtoRequest.class, name = "PASS"),
        @JsonSubTypes.Type(value = RegistrationDtoRequest.class, name = "REGISTRATION"),
        @JsonSubTypes.Type(value = SurrenderDtoRequest.class, name = "SURRENDER"),
        @JsonSubTypes.Type(value = TurnDtoRequest.class, name = "TURN"),
        @JsonSubTypes.Type(value = AnswerDtoRequest.class, name = "ANSWER")
})
public class BaseDtoRequest {

}
