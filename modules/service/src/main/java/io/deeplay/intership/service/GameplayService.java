package io.deeplay.intership.service;

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
import io.deeplay.intership.util.aggregator.AggregatorUtil;
import io.deeplay.intership.util.aggregator.DataCollectionsAggregator;
import org.apache.log4j.Logger;

public class GameplayService {
    private final Logger logger = Logger.getLogger(GameplayService.class);
    private final AggregatorUtil aggregatorUtil;
    private final EntityConverter entityConverter;

    public GameplayService(DataCollectionsAggregator collectionsAggregator) {
        this.aggregatorUtil = new AggregatorUtil(collectionsAggregator);
        this.entityConverter = new EntityConverter();
    }

    /**
     * Обрабатывает ход игрока в игре.
     *
     * @param dtoRequest {@link TurnDtoRequest}, содержащий детали хода.
     * @return {@link ActionDtoResponse}, указывающий результат хода.
     * @throws ServerException Если есть проблема с сервером.
     */
    public ActionDtoResponse turn(final TurnDtoRequest dtoRequest) throws ServerException {
        final User user = aggregatorUtil.getUserByToken(dtoRequest.token);
        final GameSession gameSession = aggregatorUtil.getGameByUserToken(dtoRequest.token);
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
        final User user = aggregatorUtil.getUserByToken(dtoRequest.token);
        final GameSession gameSession = aggregatorUtil.getGameByUserToken(dtoRequest.token);
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
}
