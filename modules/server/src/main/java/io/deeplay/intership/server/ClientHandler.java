package io.deeplay.intership.server;

import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.response.FailureDtoResponse;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.json.converter.JSONConverter;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;


public class ClientHandler implements Runnable {
    private static final AtomicInteger clientIdCounter = new AtomicInteger(1);
    private final Logger logger = Logger.getLogger(ClientHandler.class);
    private final UserController userController;
    private final GameController gameController;
    private final Socket clientSocket;
    private final JSONConverter converter;
    private final int clientId;


    public ClientHandler(Socket clientSocket, UserController userController, GameController gameController, JSONConverter converter) {
        this.userController = userController;
        this.gameController = gameController;
        this.clientSocket = clientSocket;
        this.converter = converter;
        this.clientId = clientIdCounter.getAndAdd(1);
    }

    public ClientHandler(Socket socket) {
        this(socket, new UserController(clientIdCounter.get()), new GameController(clientIdCounter.get()), new JSONConverter());
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {
            //TODO: реализовать отключение клиента от сервера, после n секунд бездействия со стороны клиента
            while (!clientSocket.isClosed()) {
                String clientCommand = in.readUTF();
                String response = defineCommand(clientCommand);
                out.writeUTF(response);
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

    public String defineCommand(final String json) {
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
            return gameController.surrenderGame(request);
        }
        if (dto instanceof final TurnDtoRequest request) {
            return gameController.turn(request);
        }
        if (dto instanceof final PassDtoRequest request) {
            return gameController.pass(request);
        }
        return getFailureResponse(new ServerException(ErrorCode.INVALID_REQUEST_TYPE));
    }

    private String getFailureResponse(ServerException ex) {
        final FailureDtoResponse dtoResponse = new FailureDtoResponse(ResponseStatus.FAILURE, ex.message);
        return converter.getJsonFromObject(dtoResponse);
    }
}
