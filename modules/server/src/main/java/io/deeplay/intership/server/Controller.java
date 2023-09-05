package io.deeplay.intership.server;

import io.deeplay.intership.dto.response.FailureDtoResponse;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.dto.validator.Validator;
import io.deeplay.intership.exception.ServerException;

public class Controller {
    protected final Validator dtoValidator;
    protected final int clientId;

    public Controller(Validator dtoValidator, int clientId) {
        this.dtoValidator = dtoValidator;
        this.clientId = clientId;
    }

    protected FailureDtoResponse getFailureResponse(ServerException ex) {
        return new FailureDtoResponse(ResponseStatus.FAILURE, ex.message);
    }
}
