package io.deeplay.intership.client;

import io.deeplay.intership.UserInterface;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.decision.maker.GameConfig;
import io.deeplay.intership.decision.maker.LoginPassword;
import io.deeplay.intership.decision.maker.terminal.DecisionMakerTerminal;
import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.response.ActionDtoResponse;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.CreateGameDtoResponse;
import io.deeplay.intership.dto.response.LoginDtoResponse;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.json.converter.JSONConverter;
import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.ui.terminal.Display;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class Client {
    private static String host;
    private static int port;
    private static Socket socket;
    private static DataInputStream reader;
    private static DataOutputStream writer;
    private static UserInterface display;
    private static DecisionMaker decisionMaker;
    private static String token;
    private static JSONConverter converter;
    private static final String CONFIG_PATH = "src/main/resources/config.properties";
    private static Color clientColor;

    public static void main(String[] args) {
        init();
        enterServer();
        joinGame();
        startGame();
    }

    public static void startGame() {
        Stone[][] field = (new Board()).getField();
        String fromServer = null;
        String toServer = null;
        GameAction action = null;

        while (true) {
            try {
                display.showBoard(field);
                display.showMoveRules();

                action = decisionMaker.getGameAction();
                switch (action.type()) {
                    case TURN ->
                            toServer = converter.getJsonFromObject(new TurnDtoRequest(clientColor.toString(), action.row() - 1, action.column() - 1, token));
                    case PASS -> toServer = converter.getJsonFromObject(new PassDtoRequest(token));
                }

                sendRequest(toServer);
                field = getField();
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
    }

    public static Stone[][] getField() {
        String fromServer;
        Stone[][] field = null;
        try {
            fromServer = reader.readUTF();
            if (isSuccessful(converter.getObjectFromJson(fromServer, BaseDtoResponse.class))) {
                field = converter.getObjectFromJson(fromServer, ActionDtoResponse.class).gameField;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return field;
    }

    public static void init() {
        boolean isGUI = false;
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH);) {
            Properties property = new Properties();
            property.load(fis);

            host = property.getProperty("client.host");
            port = Integer.parseInt(property.getProperty("client.port"));
            isGUI = Boolean.parseBoolean(property.getProperty("client.GUI"));

            socket = new Socket(host, port);
            reader = new DataInputStream(socket.getInputStream());
            writer = new DataOutputStream(socket.getOutputStream());
            converter = new JSONConverter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isGUI) {
            // ГУИ + ДМ
        } else {
            display = new Display();
            decisionMaker = new DecisionMakerTerminal(new Scanner(System.in));
        }
    }

    public static void sendRequest(String request) {
        try {
            writer.writeUTF(request);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void makeMove() {
        //обработка валидатором
        //перевести в дто и в json и отправить в writer.println()
        GameAction move = null;
        String toServer = null;
        boolean isException = true;
        display.showMoveRules();
        while (isException) {
            try {
                move = decisionMaker.getGameAction();

            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
    }


    public static boolean enterServer() {
        LoginPassword lp = null;
        boolean isException = true;
        String toServer = null;
        String fromServer = null;
        while (isException) {
            try {
                display.showAuthorizationActions();
                lp = decisionMaker.getLoginPassword();
                // Не выбирать регистрацию т.к. она не работает
                if (lp.type().equals(RequestType.REGISTRATION)) {
                    toServer = converter.getJsonFromObject(new RegistrationDtoRequest(lp.login(), lp.password()));
                } else {
                    toServer = converter.getJsonFromObject(new LoginDtoRequest(lp.login(), lp.password()));
                }
                sendRequest(toServer);
                try {
                    fromServer = reader.readUTF();
                    if (isSuccessful(converter.getObjectFromJson(fromServer, BaseDtoResponse.class))
                            && !lp.type().equals(RequestType.REGISTRATION)) {
                        token = converter.getObjectFromJson(fromServer, LoginDtoResponse.class).token;
                        isException = false;
                        display.showLogin();
                    } else {
                        isException = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (ClientException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    public static void joinGame() {
        Color color = Color.BLACK;
        String toServer = null;
        String fromServer = null;
        boolean isException = true;

        while (isException) {
            try {
                display.showRoomActions(); // Не все данные запрашиваются, надо точно дать цвет. При создании надо с ботом или без, и размер.

                GameConfig ID = decisionMaker.getGameConfig();
                clientColor = ID.color();
                switch (ID.type()) {
                    case CREATE_GAME ->
                            toServer = converter.getJsonFromObject(new CreateGameDtoRequest(ID.withBot(), ID.color().toString(), ID.size(), token));
                    case JOIN_GAME ->
                            toServer = converter.getJsonFromObject(new JoinGameDtoRequest(ID.gameId(), token, color.toString()));
                }

                sendRequest(toServer);

                try {
                    fromServer = reader.readUTF();
                    if (isSuccessful(converter.getObjectFromJson(fromServer, BaseDtoResponse.class))) {
                        if (ID.type().equals(RequestType.CREATE_GAME)) {
                            display.showCreating(converter.getObjectFromJson(fromServer, CreateGameDtoResponse.class).gameId);
                        }
                        display.showJoin();
                        isException = false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }

    }

    private static boolean isSuccessful(BaseDtoResponse dtoResponse) {
        if (dtoResponse.status.equals("success")) {
            return true;
        }
        return false;
    }
}
