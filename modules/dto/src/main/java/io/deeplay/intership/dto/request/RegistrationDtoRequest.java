package io.deeplay.intership.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrationDtoRequest extends BaseDtoRequest {
    public final String login;
    public final String passwordHash;

    @JsonCreator
    public RegistrationDtoRequest(
            @JsonProperty("login") String login,
            @JsonProperty("passwordHash") String passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
    }
}
