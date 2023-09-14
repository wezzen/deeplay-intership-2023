package io.deeplay.intership.dto.request.authorization;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.deeplay.intership.dto.request.BaseDtoRequest;

public class RegistrationDtoRequest extends BaseDtoRequest {
    public final String login;
    public final String passwordHash;

    @JsonCreator
    public RegistrationDtoRequest(
            @JsonProperty("name") String login,
            @JsonProperty("passwordHash") String passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
    }
}
