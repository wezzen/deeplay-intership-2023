package io.deeplay.intership.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
})
public class BaseDtoRequest {

}
