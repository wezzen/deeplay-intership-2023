package io.deeplay.intership.service;


import io.deeplay.intership.dto.request.LoginDtoRequest;
import io.deeplay.intership.dto.request.LogoutDtoRequest;
import io.deeplay.intership.dto.request.RegistrationDtoRequest;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.response.LoginDtoResponse;
import io.deeplay.intership.model.User;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserService {
    private final ConcurrentMap<String, User> authorizedUsers;


    public UserService() {
        this.authorizedUsers = new ConcurrentHashMap<>();
    }

    public boolean isAuthorized(final String token) {
        return authorizedUsers.containsKey(token);
    }

    public LoginDtoResponse authorization(final LoginDtoRequest dtoRequest) {
        //FIXME: заменить заглушку на работающий код
        return null;
    }

    public InfoDtoResponse register(final RegistrationDtoRequest dtoRequest) {

        return null;
    }

    public InfoDtoResponse logout(final LogoutDtoRequest dtoRequest) {
        return null;
    }
}
