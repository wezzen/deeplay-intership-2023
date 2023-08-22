package io.deeplay.intership.server;

import io.deeplay.intership.dto.ResponseStatus;
import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.response.*;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.json.converter.JSONConverter;
import io.deeplay.intership.service.GameService;
import io.deeplay.intership.service.UserService;
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
    private final int clientId;
    private final JSONConverter converter;
    private final GameService gameService;
    private final UserService userService;

    public ClientHandler(Socket socket, GameService gameService, UserService userService, JSONConverter converter) {
        this.clientSocket = socket;
        this.clientId = clientIdCounter.getAndAdd(1);
        this.converter = converter;
        this.gameService = gameService;
        this.userService = userService;
    }

    public ClientHandler(Socket socket) {
        this(socket, new GameService(), new UserService(), new JSONConverter());
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {
            while (true) {
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

    public String defineCommand(String request) {
        final BaseDto dto = converter.getObjectFromJson(request, BaseDto.class);

        return switch (dto.requestType()) {
            case REGISTRATION -> registerUser(request);
            case LOGIN -> login(request);
            case LOGOUT -> logout(request);
            case CREATE_GAME -> createGame(request);
            case JOIN_GAME -> joinGame(request);
            case SURRENDER -> surrenderGame(request);
            case FINISH_GAME -> endGame(request);
            case TURN -> turn(request);
            case PASS -> pass(request);
        };
    }

    public String registerUser(String request) {
        final String message = String.format("Client %d send registration", clientId);
        logger.debug(message);

        try {
            final RegistrationDtoRequest dto = converter.getObjectFromJson(request, RegistrationDtoRequest.class);
            final InfoDtoResponse response = userService.register(dto);
            return converter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients registration was failed");
            return getFailureResponse(ex);
        }
    }

    public String login(String request) {
        final String message = String.format("Client %d send authorization", clientId);
        logger.debug(message);

        try {
            final LoginDtoRequest dto = converter.getObjectFromJson(request, LoginDtoRequest.class);
            final LoginDtoResponse response = userService.authorization(dto);
            return converter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients login was failed");
            return getFailureResponse(ex);
        }
    }

    public String logout(String request) {
        final String message = String.format("Client %d send authorization", clientId);
        logger.debug(message);

        try {
            final LogoutDtoRequest dto = converter.getObjectFromJson(request, LogoutDtoRequest.class);
            final InfoDtoResponse response = userService.logout(dto);
            return converter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients logout was failed");
            return getFailureResponse(ex);
        }
    }

    public String createGame(String request) {
        final String message = String.format("Client %d send create game", clientId);
        logger.debug(message);

        try {
            final CreateGameDtoRequest dto = converter.getObjectFromJson(request, CreateGameDtoRequest.class);
            final CreateGameDtoResponse response = gameService.createGame(dto);
            return converter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients operation 'create game' was failed");
            return getFailureResponse(ex);
        }
    }

    public String joinGame(String request) {
        final String message = String.format("Client %d send join game", clientId);
        logger.debug(message);

        try {
            final JoinGameDtoRequest dto = converter.getObjectFromJson(request, JoinGameDtoRequest.class);
            final InfoDtoResponse response = gameService.joinGame(dto);
            return converter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients operation 'join game' was failed");
            return getFailureResponse(ex);
        }
    }

    public String surrenderGame(String request) {
        final String message = String.format("Client %d send surrender game", clientId);
        logger.debug(message);

        final SurrenderDtoRequest dto = converter.getObjectFromJson(request, SurrenderDtoRequest.class);
        final InfoDtoResponse response = gameService.surrenderGame(dto);
        return converter.getJsonFromObject(response);
    }

    public String endGame(String request) {
        final String message = String.format("Client %d send finish game", clientId);
        logger.debug(message);

        final FinishGameDtoRequest dto = converter.getObjectFromJson(request, FinishGameDtoRequest.class);
        final FinishGameDtoResponse response = gameService.finishGame(dto);
        return converter.getJsonFromObject(response);
    }

    public String turn(String request) {
        final String message = String.format("Client %d send make turn", clientId);
        logger.debug(message);

        try {
            final TurnDtoRequest dto = converter.getObjectFromJson(request, TurnDtoRequest.class);
            final ActionDtoResponse response = gameService.turn(dto);
            return converter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients operation 'turn' was failed");
            return getFailureResponse(ex);
        }
    }

    public String pass(String request) {
        final String message = String.format("Client %d send pass turn", clientId);
        logger.debug(message);

        try {
            final PassDtoRequest dto = converter.getObjectFromJson(request, PassDtoRequest.class);
            final ActionDtoResponse response = gameService.pass(dto);
            return converter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients operation 'pass' was failed");
            return getFailureResponse(ex);
        }
    }

    private String getFailureResponse(ServerException ex) {
        final FailureDtoResponse dtoResponse = new FailureDtoResponse(ex.message, ResponseStatus.FAILURE.text);
        return converter.getJsonFromObject(dtoResponse);
    }
}
