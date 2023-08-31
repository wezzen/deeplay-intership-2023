package io.deeplay.intership.sandbox.bot;

import io.deeplay.intership.bot.RandomBot;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.dto.request.CreateGameDtoRequest;
import io.deeplay.intership.dto.request.JoinGameDtoRequest;
import io.deeplay.intership.dto.request.LoginDtoRequest;
import io.deeplay.intership.dto.request.RegistrationDtoRequest;
import io.deeplay.intership.dto.response.ActionDtoResponse;
import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.service.GameService;
import io.deeplay.intership.service.UserService;

import java.util.UUID;

public class Sandbox {
    private final RandomBot blackBot;
    private String blackBotToken;
    private final RandomBot whiteBot;
    private String whiteBotToken;
    private final GameService gameService;
    private final UserService userService;
    private int runGame;

    public Sandbox() throws ServerException {
        this.gameService = new GameService();
        this.userService = new UserService();
        this.blackBot = initBotConnection(Color.BLACK);
        this.whiteBot = initBotConnection(Color.WHITE);
        this.runGame = 0;
    }

    public void startGame() throws ServerException {
        final String gameId = createGame();
        joinGame(gameId);
        ActionDtoResponse response = new ActionDtoResponse("", "", new Board().getField());

        while (runGame != 2) {
            try {
                response = turn(blackBot, response.gameField);
                response = turn(whiteBot, response.gameField);
            } catch (ServerException ex) {
                if (ex.errorCode != ErrorCode.GAME_WAS_FINISHED) {
                    throw ex;
                }
                runGame = 2;
            }
        }
    }

    private RandomBot initBotConnection(Color color) throws ServerException {
        final String login = "Bot" + UUID.randomUUID();
        final String password = UUID.randomUUID().toString();
        userService.register(new RegistrationDtoRequest(
                login,
                password));
        var response = userService.authorization(new LoginDtoRequest(
                login,
                password));

        if (color == Color.BLACK) {
            blackBotToken = response.token;
        } else {
            whiteBotToken = response.token;
        }

        return new RandomBot(response.token, color);
    }

    private String createGame() throws ServerException {
        var response = gameService.createGame(new CreateGameDtoRequest(
                true,
                Color.BLACK.name(),
                9,
                blackBotToken));
        return response.gameId;
    }

    private void joinGame(final String gameId) throws ServerException {
        gameService.joinGame(new JoinGameDtoRequest(
                gameId,
                whiteBotToken,
                Color.WHITE.name()));
    }

    private ActionDtoResponse turn(final RandomBot bot, final Stone[][] gameField) throws ServerException {
        /*
        Move move = bot.getGameAction(gameField);
        if (move.color() != Color.EMPTY.name()) {
            try {
                return gameService.turn(new TurnDtoRequest(
                        RequestType.TURN,
                        move.color(),
                        move.column(),
                        move.column(),
                        move.token()));
            } catch (ServerException ex) {
                return gameService.pass(new PassDtoRequest(RequestType.PASS, move.token()));
            }
        }
        return gameService.pass(new PassDtoRequest(RequestType.PASS, move.token()));
         */
        return null;
    }

}
