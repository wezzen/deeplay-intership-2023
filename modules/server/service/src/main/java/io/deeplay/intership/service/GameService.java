package io.deeplay.intership.service;

import io.deeplay.intership.dto.request.game.CreateGameDtoRequest;
import io.deeplay.intership.dto.request.game.JoinGameDtoRequest;
import io.deeplay.intership.dto.response.game.CreateGameDtoResponse;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Player;
import io.deeplay.intership.model.User;
import io.deeplay.intership.util.aggregator.AggregatorUtil;
import io.deeplay.intership.util.aggregator.DataCollectionsAggregator;
import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * Класс GameService обрабатывает связанные с игрой операции, такие как создание игр, присоединение к играм, выполнение ходов и т.д.
 * и управление игровыми сессиями.
 */
public class GameService {
    private final Logger logger = Logger.getLogger(GameService.class);
    private final AggregatorUtil aggregatorUtil;

    public GameService(DataCollectionsAggregator collectionsAggregator) {
        this.aggregatorUtil = new AggregatorUtil(collectionsAggregator);
    }


    /**
     * Создает новую игровую сессию на основе предоставленного CreateGameDtoRequest.
     *
     * @param dtoRequest {@link CreateGameDtoRequest}, содержащий сведения о параметрах игры.
     * @return {@link CreateGameDtoResponse}, указывающий на успешное создание игры.
     * @throws ServerException Если возникла проблема при обработке запроса.
     */
    public CreateGameDtoResponse createGame(final CreateGameDtoRequest dtoRequest) throws ServerException {
        final User user = aggregatorUtil.getUserByToken(dtoRequest.token);
        final Player player = new Player(user.login(), dtoRequest.color);

        final String gameId = UUID.randomUUID().toString();
        final GameSession gameSession = new GameSession(gameId);
        gameSession.addPlayer(player);

        aggregatorUtil.addGameSession(gameId, gameSession);
        aggregatorUtil.addUserToGame(dtoRequest.token, gameId);

        logger.debug("Game was successfully created");
        return new CreateGameDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_CREATE_GAME.message,
                gameId);
    }

    /**
     * Позволяет игроку присоединиться к существующей игровой сессии.
     *
     * @param dtoRequest {@link JoinGameDtoRequest}, содержащий сведения о присоединении к игре.
     * @return {@link InfoDtoResponse}, указывающий на успешное присоединение к игре.
     * @throws ServerException Если есть проблема с сервером.
     */
    public InfoDtoResponse joinGame(final JoinGameDtoRequest dtoRequest) throws ServerException {
        final User user = aggregatorUtil.getUserByToken(dtoRequest.token);
        final GameSession gameSession = aggregatorUtil.getGameSessionById(dtoRequest.gameId);

        final Player player = new Player(user.login(), Color.WHITE.name());
        gameSession.addPlayer(player);
        aggregatorUtil.addUserToGame(dtoRequest.token, dtoRequest.gameId);

        logger.debug("Player was successfully joined to game " + dtoRequest.gameId);
        return new InfoDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_JOIN_GAME.message);
    }
}
