package io.deeplay.intership.server.controllers;

import io.deeplay.intership.dto.response.FailureDtoResponse;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.dto.validator.Validator;
import io.deeplay.intership.exception.ServerException;

/**
 * Абстрактный базовый класс Controller, предоставляющий общий функционал контроллеров.
 * Контроллеры обрабатывают запросы от клиентов и возвращают соответствующие ответы.
 */
public abstract class Controller {
    protected final Validator dtoValidator;
    protected final int clientId;

    public Controller(final Validator dtoValidator, final int clientId) {
        this.dtoValidator = dtoValidator;
        this.clientId = clientId;
    }

    /**
     * Метод для создания объекта FailureDtoResponse на основе исключения ServerException.
     * Используется для формирования ответов с ошибкой.
     *
     * @param ex Исключение типа {@link ServerException}.
     * @return Объект {@link FailureDtoResponse} с кодом FAILURE и сообщением об ошибке.
     */
    protected FailureDtoResponse getFailureResponse(final ServerException ex) {
        return new FailureDtoResponse(ResponseStatus.FAILURE, ex.message);
    }
}
