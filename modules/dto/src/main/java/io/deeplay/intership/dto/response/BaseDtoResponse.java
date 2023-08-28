package io.deeplay.intership.dto.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "responseType",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ActionDtoResponse.class, name = "ACTION"),
        @JsonSubTypes.Type(value = CreateGameDtoResponse.class, name = "CREATE_GAME"),
        @JsonSubTypes.Type(value = FailureDtoResponse.class, name = "FAILURE"),
        @JsonSubTypes.Type(value = FinishGameDtoResponse.class, name = "FINISH_GAME"),
        @JsonSubTypes.Type(value = InfoDtoResponse.class, name = "INFO"),
        @JsonSubTypes.Type(value = LoginDtoResponse.class, name = "LOGIN")
})
public class BaseDtoResponse {

}
