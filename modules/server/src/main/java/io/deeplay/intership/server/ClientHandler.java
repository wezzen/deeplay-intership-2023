package io.deeplay.intership.server;

import io.deeplay.intership.connection.ServerStreamConnector;
import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.FailureDtoResponse;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.util.aggregator.DataCollectionsAggregator;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;


public class ClientHandler implements Runnable {
    private static final AtomicInteger clientIdCounter = new AtomicInteger(1);
    private final Logger logger = Logger.getLogger(ClientHandler.class);
    private final Socket clientSocket;
    private final ServerStreamConnector streamConnector;
    private final UserController userController;
    private final GameController gameController;
    private final GameplayController gameplayController;


    public ClientHandler(
            Socket clientSocket,
            UserController userController,
            GameController gameController,
            GameplayController gameplayController) throws IOException {
        this.clientSocket = clientSocket;
        this.userController = userController;
        this.gameController = gameController;
        this.gameplayController = gameplayController;
        this.streamConnector = new ServerStreamConnector(
                new DataOutputStream(clientSocket.getOutputStream()),
                new DataInputStream(clientSocket.getInputStream())
        );
        clientIdCounter.getAndAdd(1);
    }

    public ClientHandler(Socket socket, DataCollectionsAggregator collectionsAggregator) throws IOException {
        this(
                socket,
                new UserController(collectionsAggregator, clientIdCounter.get()),
                new GameController(collectionsAggregator, clientIdCounter.get()),
                new GameplayController(collectionsAggregator, clientIdCounter.get()));
    }

    @Override
    public void run() {
        try {
            //TODO: реализовать отключение клиента от сервера, после n секунд бездействия со стороны клиента
            while (!clientSocket.isClosed()) {
                BaseDtoRequest dtoRequest = streamConnector.getRequest();
                BaseDtoResponse dtoResponse = defineCommand(dtoRequest);
                streamConnector.sendResponse(dtoResponse);
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

    public BaseDtoResponse defineCommand(final BaseDtoRequest dtoRequest) {
        if (dtoRequest instanceof final RegistrationDtoRequest request) {
            return userController.registerUser(request);
        }
        if (dtoRequest instanceof final LoginDtoRequest request) {
            return userController.login(request);
        }
        if (dtoRequest instanceof final LogoutDtoRequest request) {
            return userController.logout(request);
        }
        if (dtoRequest instanceof final CreateGameDtoRequest request) {
            return gameController.createGame(request);
        }
        if (dtoRequest instanceof final JoinGameDtoRequest request) {
            return gameController.joinGame(request);
        }
        if (dtoRequest instanceof final SurrenderDtoRequest request) {
            return gameplayController.surrenderGame(request);
        }
        if (dtoRequest instanceof final TurnDtoRequest request) {
            return gameplayController.turn(request);
        }
        if (dtoRequest instanceof final PassDtoRequest request) {
            return gameplayController.pass(request);
        }
        return new FailureDtoResponse(ResponseStatus.FAILURE, ResponseInfoMessage.SUCCESS_FINISH_GAME.message);
    }

}
