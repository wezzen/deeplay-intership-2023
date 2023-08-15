package io.deeplay.intership.service;

import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.response.ActionDtoResponse;
import io.deeplay.intership.dto.response.CreateGameDtoResponse;
import io.deeplay.intership.dto.response.FinishGameDtoResponse;
import io.deeplay.intership.dto.response.InfoDtoResponse;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GameService {
    private final ConcurrentMap<String, GameSession> games;

    public GameService() {
        //FIXME: заменить заглушку на работающий код
        this.games = new ConcurrentHashMap<>();
    }

    public CreateGameDtoResponse createGame(final CreateGameDtoRequest dtoRequest) {
        return null;
    }

    public InfoDtoResponse joinGame(final JoinGameDtoRequest dtoRequest) {
        return null;
    }

    public InfoDtoResponse surrenderGame(final SurrenderDtoRequest dtoRequest) {
        return null;
    }

    public FinishGameDtoResponse endGame(final FinishGameDtoRequest dtoRequest) {
        return null;
    }

    public ActionDtoResponse turn(final TurnDtoRequest dtoRequest) {
        return null;
    }

    public ActionDtoResponse pass(final PassDtoRequest dtoRequest) {
        return null;
    }
}
