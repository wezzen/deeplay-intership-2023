package io.deeplay.intership.service;

import io.deeplay.intership.dao.GameDao;
import io.deeplay.intership.dao.UserDao;
import io.deeplay.intership.dao.file.GameDaoImpl;
import io.deeplay.intership.dao.file.UserDaoImpl;
import io.deeplay.intership.dto.request.PassDtoRequest;
import io.deeplay.intership.dto.request.SurrenderDtoRequest;
import io.deeplay.intership.dto.request.TurnDtoRequest;
import io.deeplay.intership.dto.response.*;
import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.Score;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.model.User;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentMap;

public class GameplayService {
    private final Logger logger = Logger.getLogger(GameplayService.class);
    private final ConcurrentMap<String, GameSession> idToGameSession;
    private final EntityConverter entityConverter;
    private final UserDao userDao;
    private final GameDao gameDao;

    public GameplayService(ConcurrentMap<String, GameSession> idToGameSession) {
        this.idToGameSession = idToGameSession;
        this.entityConverter = new EntityConverter();
        this.userDao = new UserDaoImpl();
        this.gameDao = new GameDaoImpl();
    }

    /**
     * Обрабатывает ход игрока в игре.
     *
     * @param dtoRequest {@link TurnDtoRequest}, содержащий детали хода.
     * @return {@link ActionDtoResponse}, указывающий результат хода.
     * @throws ServerException Если есть проблема с сервером.
     */
    public ActionDtoResponse turn(final TurnDtoRequest dtoRequest) throws ServerException {
        final User user = userDao.getUserByToken(dtoRequest.token);
        final GameSession gameSession = findGameSessionById(gameDao.getGameIdByPlayerLogin(dtoRequest.token));
        final Stone stone = entityConverter.turnDtoToModel(dtoRequest);

        try {
            final Stone[][] gameField = gameSession.turn(user.login(), stone);
            logger.debug("Player was successfully make turn");
            return new ActionDtoResponse(
                    ResponseStatus.SUCCESS,
                    ResponseInfoMessage.SUCCESS_TURN.message,
                    gameField);
        } catch (ServerException ex) {
            if (ex.errorCode == ErrorCode.GAME_WAS_FINISHED) {
                finishGame(gameSession);
            }
            throw ex;
        }
    }

    /**
     * Позволяет игроку пройти свой ход в игре.
     *
     * @param dtoRequest {@link PassDtoRequest}, содержащий сведения о проходе.
     * @return {@link ActionDtoResponse}, указывающий на успешное прохождение поворота.
     * @throws ServerException Если есть проблема с сервером.
     */
    public BaseDtoResponse pass(final PassDtoRequest dtoRequest) throws ServerException {
        final User user = userDao.getUserByToken(dtoRequest.token);
        final GameSession gameSession = findGameSessionById(gameDao.getGameIdByPlayerLogin(dtoRequest.token));
        try {
            final Stone[][] gameField = gameSession.pass(user.login());
            return new ActionDtoResponse(
                    ResponseStatus.SUCCESS,
                    ResponseInfoMessage.SUCCESS_PASS.message,
                    gameField);
        } catch (ServerException ex) {
            if (ex.errorCode == ErrorCode.GAME_WAS_FINISHED) {
                return finishGame(gameSession);
            }
            throw ex;
        }
    }

    public InfoDtoResponse surrenderGame(final SurrenderDtoRequest dtoRequest) throws ServerException {
        return null;
    }

    public FinishGameDtoResponse finishGame(final GameSession gameSession) {
        final Score score = gameSession.getGameScore();
        return new FinishGameDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_FINISH_GAME.message,
                score.blackPoints(),
                score.whitePoints());
    }

    private GameSession findGameSessionById(final String gameId) throws ServerException {
        GameSession gameSession = idToGameSession.get(gameId);
        if (gameSession == null) {
            throw new ServerException(ErrorCode.GAME_NOT_FOUND);
        }
        return gameSession;
    }
}
