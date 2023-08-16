package io.deeplay.intership.server;

import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.response.*;
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
    private final Converter converter;
    private final GameService gameService;
    private final UserService userService;

    public ClientHandler(Socket socket, GameService gameService, UserService userService, Converter converter) {
        this.clientSocket = socket;
        this.clientId = clientIdCounter.getAndAdd(1);
        this.converter = converter;
        this.gameService = gameService;
        this.userService = userService;
    }

    public ClientHandler(Socket socket) {
        this(socket, new GameService(), new UserService(), new Converter());
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
        BaseDto dto = converter.getClassFromJson(request, BaseDto.class);

        return switch (dto.requestType) {
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
        String message = String.format("Client %d send registration", clientId);
        logger.debug(message);

        RegistrationDtoRequest dto = converter.getClassFromJson(request, RegistrationDtoRequest.class);
        InfoDtoResponse response = userService.register(dto);
        return converter.objectToJson(response);
    }

    public String login(String request) {
        String message = String.format("Client %d send authorization", clientId);
        logger.debug(message);

        LoginDtoRequest dto = converter.getClassFromJson(request, LoginDtoRequest.class);
        LoginDtoResponse response = userService.authorization(dto);
        return converter.objectToJson(response);
    }

    public String logout(String request) {
        String message = String.format("Client %d send authorization", clientId);
        logger.debug(message);

        LogoutDtoRequest dto = converter.getClassFromJson(request, LogoutDtoRequest.class);
        var response = userService.logout(dto);
        return converter.objectToJson(response);
    }

    public String createGame(String request) {
        CreateGameDtoRequest dto = converter.getClassFromJson(request, CreateGameDtoRequest.class);
        var response = gameService.createGame(dto);
        return converter.objectToJson(response);
    }

    public String joinGame(String request) {
        JoinGameDtoRequest dto = converter.getClassFromJson(request, JoinGameDtoRequest.class);
        InfoDtoResponse response = gameService.joinGame(dto);
        return converter.objectToJson(response);
    }

    public String surrenderGame(String request) {
        SurrenderDtoRequest dto = converter.getClassFromJson(request, SurrenderDtoRequest.class);
        InfoDtoResponse response = gameService.surrenderGame(dto);
        return converter.objectToJson(response);
    }

    public String endGame(String request) {
        FinishGameDtoRequest dto = converter.getClassFromJson(request, FinishGameDtoRequest.class);
        FinishGameDtoResponse response = gameService.endGame(dto);
        return converter.objectToJson(response);
    }

    public String turn(String request) {
        TurnDtoRequest dto = converter.getClassFromJson(request, TurnDtoRequest.class);
        ActionDtoResponse response = gameService.turn(dto);
        return converter.objectToJson(response);
    }

    public String pass(String request) {
        PassDtoRequest dto = converter.getClassFromJson(request, PassDtoRequest.class);
        ActionDtoResponse response = gameService.pass(dto);
        return converter.objectToJson(response);
    }
}
