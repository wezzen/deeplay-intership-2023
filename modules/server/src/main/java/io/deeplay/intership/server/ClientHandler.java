package io.deeplay.intership.server;

import io.deeplay.intership.dto.Dto;
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

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        this.clientId = clientIdCounter.getAndAdd(1);
        this.converter = new Converter();
        this.gameService = new GameService();
        this.userService = new UserService();
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
        Dto dto = converter.JsonToObject(request);

        return switch (ClientCommand.valueOf(dto.command())) {
            case REGISTRATION -> registerUser(request);
            case LOGIN -> login(request);
            case LOGOUT -> logout(request);
            case CREATE_GAME -> createGame(request);
            case JOIN_GAME -> joinGame(request);
            case SURRENDER_GAME -> surrenderGame(request);
            case END_GAME -> endGame(request);
            case TURN -> turn(request);
            case PASS -> pass(request);
            default -> unknownCommand();
        };
    }

    public String registerUser(String request) {
        //FIXME: В сервисы передавать готовые dto, из сервисов возвращать готовые response
        return userService.register(request);
    }

    public String login(String request) {
        return userService.authorization(request);
    }

    public String logout(String request) {
        return userService.logout(request);
    }

    public String createGame(String request) {
        return gameService.createGame(request);
    }

    public String joinGame(String request) {
        return gameService.joinGame(request);
    }

    public String surrenderGame(String request) {
        return gameService.surrenderGame(request);
    }

    public String endGame(String request) {
        return endGame(request);
    }

    public String turn(String request) {
        return gameService.turn(request);
    }

    public String pass(String request) {
        return gameService.pass(request);
    }

    public String unknownCommand() {
        return null;
    }
}
