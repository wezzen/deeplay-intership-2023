package io.deeplay.intership.server;

import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.FailureDtoResponse;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.dto.response.gameplay.AnswerDtoResponse;
import io.deeplay.intership.dto.response.gameplay.FinishGameDtoResponse;
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

public class ServerGame extends Thread {
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
        player.startGame();
    }

    @Override
    public void run() {
        while (!isCompletable()) {

        }
        gameSession.startGame();
        try {
            while (!gameSession.isFinished()) {
                try {
                    int nextPlayerToAction = gameSession.getNextPlayer();
                    GoPlayer player = players[nextPlayerToAction];

                    ((ServerGoPlayer) player).sendResponse(notifyAnswer(gameSession.getGameField()));
                    Answer answer = players[nextPlayerToAction].getGameAction();

                    var res = switch (answer.answerType()) {
                        case TURN -> turn(player.getName(), player.getColor(), answer);
                        case PASS -> pass(player.getName());
                        default -> throw new IllegalStateException();
                    };
                    ((ServerGoPlayer) player).sendResponse(res);
                } catch (ServerException ex) {
                    ((ServerGoPlayer) players[gameSession.getNextPlayer()]).sendResponse(getFailure(ex));
                }
            }
            endGame();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean isCompletable() {
        return Arrays.stream(players).noneMatch(Objects::isNull);
    }

    public boolean isFinished() {
        return gameSession.isFinished();
    }

    public void endGame() throws IOException {
        for (var player : players) {
            ((ServerGoPlayer) player).sendResponse(new FinishGameDtoResponse(
                    ResponseStatus.SUCCESS,
                    ResponseInfoMessage.SUCCESS_FINISH_GAME.message,
                    gameSession.getGameScore().blackPoints(),
                    gameSession.getGameScore().whitePoints()));
            player.endGame();
        }
    }

    public BaseDtoResponse notifyAnswer(final Stone[][] gameField) throws IOException {
        final Stone[][] fieldCopy = copyField(gameField);
        return new AnswerDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.CAN_TURN.message,
                fieldCopy);
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

    private Stone[][] copyField(final Stone[][] gameField) {
        final Stone[][] copy = new Stone[gameField.length][gameField.length];
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                copy[i][j] = new Stone(
                        gameField[i][j].getColor(),
                        gameField[i][j].getRowNumber(),
                        gameField[i][j].getColumnNumber());
            }
        }
        return copy;
    }
}
