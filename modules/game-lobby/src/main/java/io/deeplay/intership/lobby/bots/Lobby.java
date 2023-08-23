package io.deeplay.intership.lobby.bots;

import io.deeplay.intership.bot.RandomBot;
import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.response.ActionDtoResponse;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Move;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.service.GameService;
import io.deeplay.intership.service.UserService;

import java.util.UUID;

public class Lobby {
    private final RandomBot blackBot;
    private String blackBotToken;
    private final RandomBot whiteBot;
    private String whiteBotToken;
    private final GameService gameService;
    private final UserService userService;

    public Lobby() throws ServerException {
        this.gameService = new GameService();
        this.userService = new UserService();
        this.blackBot = initBotConnection(Color.BLACK);
        this.whiteBot = initBotConnection(Color.WHITE);
    }

    public RandomBot initBotConnection(Color color) throws ServerException {
        final String login = "Bot " + UUID.randomUUID();
        final String password = UUID.randomUUID().toString();
        userService.register(new RegistrationDtoRequest(
                RequestType.REGISTRATION,
                login,
                password));
        var response = userService.authorization(new LoginDtoRequest(
                RequestType.LOGIN,
                login,
                password));

        if (color == Color.BLACK) {
            blackBotToken = response.token();
        } else {
            whiteBotToken = response.token();
        }

        return new RandomBot(response.token(), color);
    }

    public void startGame() throws ServerException {
        String gameId = createGame();
        joinGame(gameId);
        ActionDtoResponse response = new ActionDtoResponse("", "", new Board().getField());
        while (true) {
            response = turn(blackBot, response.gameField());
            response = turn(whiteBot, response.gameField());
        }
    }

    private String createGame() throws ServerException {
        var response = gameService.createGame(new CreateGameDtoRequest(
                RequestType.CREATE_GAME,
                true,
                Color.BLACK.name(),
                9,
                blackBotToken));
        return response.gameId();
    }

    private void joinGame(final String gameId) throws ServerException {
        gameService.joinGame(new JoinGameDtoRequest(
                RequestType.JOIN_GAME,
                gameId,
                whiteBotToken,
                Color.WHITE.name()));
    }

    private ActionDtoResponse turn(final RandomBot bot, final Stone[][] gameField) {
        Move move = bot.chooseGameAction(gameField);
        if (move.color() != Color.EMPTY.name()) {
            return gameService.turn(new TurnDtoRequest(
                    RequestType.TURN,
                    move.color(),
                    move.row(),
                    move.column(),
                    move.token()));
        } else {
            return gameService.pass(new PassDtoRequest(
                    RequestType.PASS,
                    move.token()));
        }

    }
}
