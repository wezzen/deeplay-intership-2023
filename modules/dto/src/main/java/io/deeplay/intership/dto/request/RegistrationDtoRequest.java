package io.deeplay.intership.dto.request;

import io.deeplay.intership.dto.RequestType;

public class RegistrationDtoRequest extends BaseDto{
    public final String login;
    public final String passwordHash;

    public RegistrationDtoRequest(RequestType requestType, String login, String passwordHash) {
        super(requestType);
        this.login = login;
        this.passwordHash = passwordHash;
    }
}
