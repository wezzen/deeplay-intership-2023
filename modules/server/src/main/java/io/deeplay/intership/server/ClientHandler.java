package io.deeplay.intership.server;

import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.response.*;
import io.deeplay.intership.exception.ServerErrorCode;
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
            return registerUser(request);
        }
        if (dto instanceof final LoginDtoRequest request) {
            return login(request);
        }
        if (dto instanceof final LogoutDtoRequest request) {
            return logout(request);
        }
        if (dto instanceof final CreateGameDtoRequest request) {
            return createGame(request);
        }
        if (dto instanceof final JoinGameDtoRequest request) {
            return joinGame(request);
        }
        if (dto instanceof final SurrenderDtoRequest request) {
            return surrenderGame(request);
        }
        if (dto instanceof final FinishGameDtoRequest request) {
            return endGame(request);
        }
        if (dto instanceof final TurnDtoRequest request) {
            return turn(request);
        }
        if (dto instanceof final PassDtoRequest request) {
            return pass(request);
        }
        return getFailureResponse(new ServerException(ServerErrorCode.INVALID_REQUEST_TYPE));
    }

    public String registerUser(RegistrationDtoRequest dtoRequest) {
        final String message = String.format("Client %d send registration", clientId);
        logger.debug(message);

        try {
            final InfoDtoResponse response = userService.register(dtoRequest);
            return converter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients registration was failed");
            return getFailureResponse(ex);
        }
    }

    public String login(LoginDtoRequest dtoRequest) {
        final String message = String.format("Client %d send authorization", clientId);
        logger.debug(message);

        try {
            final LoginDtoResponse response = userService.authorization(dtoRequest);
            return converter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients login was failed");
            return getFailureResponse(ex);
        }
    }

    public String logout(LogoutDtoRequest dtoRequest) {
        final String message = String.format("Client %d send authorization", clientId);
        logger.debug(message);

        try {
            final InfoDtoResponse response = userService.logout(dtoRequest);
            return converter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients logout was failed");
            return getFailureResponse(ex);
        }
    }

    public String createGame(CreateGameDtoRequest dtoRequest) {
        final String message = String.format("Client %d send create game", clientId);
        logger.debug(message);

        try {
            final CreateGameDtoResponse response = gameService.createGame(dtoRequest);
            return converter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients operation 'create game' was failed");
            return getFailureResponse(ex);
        }
    }

    public String joinGame(JoinGameDtoRequest dtoRequest) {
        final String message = String.format("Client %d send join game", clientId);
        logger.debug(message);

        try {
            final InfoDtoResponse response = gameService.joinGame(dtoRequest);
            return converter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients operation 'join game' was failed");
            return getFailureResponse(ex);
        }
    }

    public String surrenderGame(SurrenderDtoRequest dtoRequest) {
        final String message = String.format("Client %d send surrender game", clientId);
        logger.debug(message);

        final InfoDtoResponse response = gameService.surrenderGame(dtoRequest);
        return converter.getJsonFromObject(response);
    }

    public String endGame(FinishGameDtoRequest dtoRequest) {
        final String message = String.format("Client %d send finish game", clientId);
        logger.debug(message);

        final FinishGameDtoResponse response = gameService.finishGame(dtoRequest);
        return converter.getJsonFromObject(response);
    }

    public String endGame() {
        return converter.getJsonFromObject(new FinishGameDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_FINISH_GAME.message,
                0,
                7));
    }

    public String turn(TurnDtoRequest dtoRequest) {
        final String message = String.format("Client %d send make turn", clientId);
        logger.debug(message);

        try {
            final ActionDtoResponse response = gameService.turn(dtoRequest);
            return converter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients operation 'turn' was failed");
            return getFailureResponse(ex);
        }
    }

    public String pass(PassDtoRequest dtoRequest) {
        final String message = String.format("Client %d send pass turn", clientId);
        logger.debug(message);

        try {
            final ActionDtoResponse response = gameService.pass(dtoRequest);
            return converter.getJsonFromObject(response);
        } catch (ServerException ex) {
            if (ex.serverErrorCode == ServerErrorCode.GAME_WAS_FINISHED) {
                return endGame();
            }
            logger.debug("Clients operation 'pass' was failed");
            return getFailureResponse(ex);
        }
    }

    private String getFailureResponse(ServerException ex) {
        final FailureDtoResponse dtoResponse = new FailureDtoResponse(ResponseStatus.FAILURE, ex.message);
        return converter.getJsonFromObject(dtoResponse);
    }
}
