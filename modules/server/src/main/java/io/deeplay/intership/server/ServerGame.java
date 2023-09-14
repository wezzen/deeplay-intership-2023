package io.deeplay.intership.server;

import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.FailureDtoResponse;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.dto.response.gameplay.AnswerDtoResponse;
import io.deeplay.intership.dto.response.gameplay.UpdateFieldDtoResponse;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.Answer;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.GoPlayer;
import io.deeplay.intership.model.Stone;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class ServerGame implements Runnable {
    private static final int PLAYERS_COUNT = 2;
    private final GoPlayer[] players = new GoPlayer[PLAYERS_COUNT];
    private final GameSession gameSession;
    private int totalPlayers;

    public ServerGame(GameSession gameSession) {
        this.gameSession = gameSession;
        this.totalPlayers = 0;
    }

    public void joinPlayer(final GoPlayer player) {
        players[totalPlayers++] = player;
    }

    @Override
    public void run() {
        gameSession.startGame();
        System.out.println("Enter into Server game");
        while (!gameSession.isFinished()) {
            try {
                final int nextPlayerToAction = gameSession.getNextPlayer();
                final GoPlayer player = players[nextPlayerToAction];

                ((ServerGoPlayer) player).sendResponse(notifyAnswer(gameSession.getGameField()));
                notifyAnswer(gameSession.getGameField());
                final Answer answer = players[nextPlayerToAction].getGameAction();

                var res = switch (answer.answerType()) {
                    case TURN -> turn(player.getName(), player.getColor(), answer);
                    case PASS -> pass(player.getName());
                    default -> throw new IllegalStateException();
                };
                ((ServerGoPlayer) player).sendResponse(res);
            } catch (ServerException ex) {

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public boolean isCompletable() {
        return Arrays.stream(players).noneMatch(Objects::isNull);
    }

    public boolean isFinished() {
        return gameSession.isFinished();
    }

    public void startGame() {
        for (var player : players) {
            player.startGame();
        }
    }

    public void endGame() {
        for (var player : players) {
            player.endGame();
        }
    }

    private BaseDtoResponse turn(final String name, final Color color, final Answer answer) throws ServerException, IOException {
        var response = gameSession.turn(
                name
                , new Stone(
                        color,
                        answer.row(),
                        answer.column()));
        return new UpdateFieldDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_TURN.message,
                response);
    }

    private BaseDtoResponse pass(final String name) throws ServerException, IOException {
        var response = gameSession.pass(name);
        return new UpdateFieldDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_PASS.message,
                response);
    }

    private FailureDtoResponse getFailure(ServerException ex) {
        return new FailureDtoResponse(ResponseStatus.FAILURE, ex.message);
    }

    private BaseDtoResponse notifyAnswer(final Stone[][] gameField) throws IOException {
        return new AnswerDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_PASS.message,
                gameField);
    }
}
