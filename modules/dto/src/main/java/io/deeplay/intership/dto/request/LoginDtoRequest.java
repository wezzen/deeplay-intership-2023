package io.deeplay.intership.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginDtoRequest extends BaseDtoRequest {
    public final String login;
    public final String passwordHash;

    public LoginDtoRequest(
            @JsonProperty("login") String login,
            @JsonProperty("passwordHash") String passwordHash) {
        super(RequestType.LOGIN);
        this.login = login;
        this.passwordHash = passwordHash;
    }
}
