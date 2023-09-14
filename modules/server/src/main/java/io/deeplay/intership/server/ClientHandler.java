package io.deeplay.intership.server;

import io.deeplay.intership.connection.StreamConnector;
import io.deeplay.intership.dto.request.BaseDtoRequest;
import io.deeplay.intership.dto.request.authorization.LoginDtoRequest;
import io.deeplay.intership.dto.request.authorization.LogoutDtoRequest;
import io.deeplay.intership.dto.request.authorization.RegistrationDtoRequest;
import io.deeplay.intership.dto.request.game.CreateGameDtoRequest;
import io.deeplay.intership.dto.request.game.JoinGameDtoRequest;
import io.deeplay.intership.dto.response.*;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.GoPlayer;
import io.deeplay.intership.server.controllers.GameController;
import io.deeplay.intership.server.controllers.UserController;
import io.deeplay.intership.util.aggregator.AggregatorUtil;
import io.deeplay.intership.util.aggregator.DataCollectionsAggregator;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ClientHandler implements Runnable {
    private static final AtomicInteger clientIdCounter = new AtomicInteger(1);
    private final Logger logger = Logger.getLogger(ClientHandler.class);
    private final Lock lock = new ReentrantLock();
    private final Socket clientSocket;
    private final StreamConnector streamConnector;
    private final UserController userController;
    private final GameController gameController;
    private final GameManager gameManager;
    private AggregatorUtil aggregatorUtil;


    public ClientHandler(
            Socket clientSocket,
            UserController userController,
            GameController gameController,
            GameManager gameManager) throws IOException {
        this.clientSocket = clientSocket;
        this.userController = userController;
        this.gameController = gameController;
        this.gameManager = gameManager;
        this.streamConnector = new StreamConnector(
                new DataOutputStream(clientSocket.getOutputStream()),
                new DataInputStream(clientSocket.getInputStream())
        );
        this.aggregatorUtil = new AggregatorUtil(null);
        clientIdCounter.getAndAdd(1);
    }

    public ClientHandler(Socket socket, DataCollectionsAggregator collectionsAggregator, GameManager gameManager) throws IOException {
        this(
                socket,
                new UserController(collectionsAggregator, clientIdCounter.get()),
                new GameController(collectionsAggregator, clientIdCounter.get()),
                gameManager);
        this.aggregatorUtil = new AggregatorUtil(collectionsAggregator);
    }

    @Override
    public void run() {
        try {
            while (!clientSocket.isClosed()) {
                lock.lock();
                logger.debug("ClientHandler завладел locker");
                BaseDtoRequest dtoRequest = streamConnector.getRequest();
                lock.unlock();
                defineCommand(dtoRequest);
            }
        } catch (IOException e) {
            logger.debug(e.getMessage());
        } finally {
            try {
                clientSocket.close();
                logger.info("Client connection was closed");
            } catch (IOException e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public BaseDtoResponse defineCommand(final BaseDtoRequest dtoRequest) throws IOException {
        if (dtoRequest instanceof final RegistrationDtoRequest request) {
            var response = userController.registerUser(request);
            streamConnector.sendResponse(response);
            return response;
        }
        if (dtoRequest instanceof final LoginDtoRequest request) {
            var response = userController.login(request);
            streamConnector.sendResponse(response);
            return response;
        }
        if (dtoRequest instanceof final LogoutDtoRequest request) {
            var response = userController.logout(request);
            streamConnector.sendResponse(response);
            return response;
        }
        if (dtoRequest instanceof final CreateGameDtoRequest request) {
            final BaseDtoResponse response = gameController.createGame(request);
            streamConnector.sendResponse(response);
            if (response instanceof final CreateGameDtoResponse dtoResponse) {
                try {
                    final GameSession gameSession = aggregatorUtil.getGameSessionById(dtoResponse.gameId);
                    final ServerGame serverGame = new ServerGame(gameSession);
                    final String name = aggregatorUtil.getUserByToken(request.token).login();
                    final GoPlayer player = new ServerGoPlayer(streamConnector, lock, name, Color.valueOf(request.color));
                    serverGame.joinPlayer(player);
                    gameManager.gameMap.put(dtoResponse.gameId, serverGame);
                    final Thread thread = new Thread(new PlayerThread(serverGame));
                    thread.start();
                    thread.join();
                } catch (ServerException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return response;
        }
        if (dtoRequest instanceof final JoinGameDtoRequest request) {
            final BaseDtoResponse response = gameController.joinGame(request);
            streamConnector.sendResponse(response);
            if (response instanceof final InfoDtoResponse dtoResponse) {
                final ServerGame serverGame = gameManager.gameMap.get(request.gameId);
                try {
                    final String name = aggregatorUtil.getUserByToken(request.token).login();
                    serverGame.joinPlayer(new ServerGoPlayer(streamConnector, lock, name, Color.valueOf(request.color)));
                    startGame(serverGame);
                    final Thread thread = new Thread(new PlayerThread(serverGame));
                    thread.start();
                    thread.join();
                } catch (InterruptedException | ServerException ex) {
                    throw new RuntimeException(ex);
                }
            }
            return response;
        }
        if (dtoRequest instanceof BaseDtoRequest) {
            logger.debug("Пропущен REQUEST от клиента ");
            return null;
        }
        return new FailureDtoResponse(ResponseStatus.FAILURE, ResponseInfoMessage.SUCCESS_FINISH_GAME.message);
    }

    private void startGame(final ServerGame serverGame) {
        if (serverGame.isCompletable()) {
            serverGame.run();
            serverGame.run();
        }
    }
}
