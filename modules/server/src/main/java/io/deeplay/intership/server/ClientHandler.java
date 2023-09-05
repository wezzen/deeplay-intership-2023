package io.deeplay.intership.server;

import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.FailureDtoResponse;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.json.converter.JSONConverter;
import io.deeplay.intership.model.Player;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;


public class ClientHandler implements Runnable {
    private static final AtomicInteger clientIdCounter = new AtomicInteger(1);
    private final Logger logger = Logger.getLogger(ClientHandler.class);
    private final Socket clientSocket;
    private final UserController userController;
    private final GameController gameController;
    private final GameplayController gameplayController;
    private final JSONConverter converter;


    public ClientHandler(
            Socket clientSocket,
            UserController userController,
            GameController gameController,
            GameplayController gameplayController,
            JSONConverter converter) {
        this.clientSocket = clientSocket;
        this.userController = userController;
        this.gameController = gameController;
        this.gameplayController = gameplayController;
        this.converter = converter;
        clientIdCounter.getAndAdd(1);
    }

    public ClientHandler(
            Socket socket,
            ConcurrentMap<String, GameSession> idToGameSession,
            ConcurrentMap<Player, String> playerToGame) {
        this(
                socket,
                new UserController(clientIdCounter.get()),
                new GameController(idToGameSession, playerToGame, clientIdCounter.get()),
                new GameplayController(idToGameSession, playerToGame, clientIdCounter.get()),
                new JSONConverter());
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {
            //TODO: реализовать отключение клиента от сервера, после n секунд бездействия со стороны клиента
            while (!clientSocket.isClosed()) {
                String clientCommand = in.readUTF();
                BaseDtoResponse dtoResponse = defineCommand(clientCommand);
                out.writeUTF(converter.getJsonFromObject(dtoResponse));
                out.flush();
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

    public BaseDtoResponse defineCommand(final String json) {
        final BaseDtoRequest dto = converter.getObjectFromJson(json, BaseDtoRequest.class);

        if (dto instanceof final RegistrationDtoRequest request) {
            return userController.registerUser(request);
        }
        if (dto instanceof final LoginDtoRequest request) {
            return userController.login(request);
        }
        if (dto instanceof final LogoutDtoRequest request) {
            return userController.logout(request);
        }
        if (dto instanceof final CreateGameDtoRequest request) {
            return gameController.createGame(request);
        }
        if (dto instanceof final JoinGameDtoRequest request) {
            return gameController.joinGame(request);
        }
        if (dto instanceof final SurrenderDtoRequest request) {
            return gameplayController.surrenderGame(request);
        }
        if (dto instanceof final TurnDtoRequest request) {
            return gameplayController.turn(request);
        }
        if (dto instanceof final PassDtoRequest request) {
            return gameplayController.pass(request);
        }
        return new FailureDtoResponse(ResponseStatus.FAILURE, ResponseInfoMessage.SUCCESS_FINISH_GAME.message);
    }

}
