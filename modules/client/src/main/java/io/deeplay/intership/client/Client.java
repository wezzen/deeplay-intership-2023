package io.deeplay.intership.client;

import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.DecisionMakerTerminal;
import io.deeplay.intership.decision.maker.LoginPassword;
import io.deeplay.intership.dto.request.LoginDtoRequest;
import io.deeplay.intership.dto.request.RegistrationDtoRequest;
import io.deeplay.intership.dto.response.LoginDtoResponse;
import io.deeplay.intership.json.converter.JSONConverter;
import io.deeplay.intership.ui.terminal.Display;
import io.deeplay.intership.ui.terminal.UserInterface;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

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

    public static void main(String[] args) {
    }

    public static void init() {
        FileInputStream fis;
        Properties property = new Properties();
        boolean isGUI = false;
        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);
            host = property.getProperty("client.host");
            port = Integer.parseInt(property.getProperty("client.port"));
            isGUI = Boolean.parseBoolean(property.getProperty("client.GUI"));
            fis.close();

            socket = new Socket(host, port);
            reader = new DataInputStream(socket.getInputStream());
            writer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        converter = new JSONConverter();
        if (isGUI) {
            //конструктор для гуи и дм
        } else {
            display = new Display();
            decisionMaker = new DecisionMakerTerminal();
        }
    }

    public static void sendRequest() {
        System.out.print("Выберите игровую сессию\n");
        System.out.print("Выберите следующее действие:\n");
        System.out.print("Чтобы подключиться к существующей сессии нажмите " + 1 + "\n");
        System.out.print("Чтобы создать сессию нажмите " + 2 + "\n");System.out.print("Выберите игровую сессию\n");
        System.out.print("Выберите следующее действие:\n");
        System.out.print("Чтобы подключиться к существующей сессии нажмите " + 1 + "\n");
        System.out.print("Чтобы создать сессию нажмите " + 2 + "\n");System.out.print("Выберите игровую сессию\n");
        System.out.print("Выберите следующее действие:\n");
        System.out.print("Чтобы подключиться к существующей сессии нажмите " + 1 + "\n");
        System.out.print("Чтобы создать сессию нажмите " + 2 + "\n");System.out.print("Выберите игровую сессию\n");
        System.out.print("Выберите следующее действие:\n");
        System.out.print("Чтобы подключиться к существующей сессии нажмите " + 1 + "\n");
        System.out.print("Чтобы создать сессию нажмите " + 2 + "\n");System.out.print("Выберите игровую сессию\n");
        System.out.print("Выберите следующее действие:\n");
        System.out.print("Чтобы подключиться к существующей сессии нажмите " + 1 + "\n");
        System.out.print("Чтобы создать сессию нажмите " + 2 + "\n");System.out.print("Выберите игровую сессию\n");
        System.out.print("Выберите следующее действие:\n");
        System.out.print("Чтобы подключиться к существующей сессии нажмите " + 1 + "\n");
        System.out.print("Чтобы создать сессию нажмите " + 2 + "\n");System.out.print("Выберите игровую сессию\n");
        System.out.print("Выберите следующее действие:\n");
        System.out.print("Чтобы подключиться к существующей сессии нажмите " + 1 + "\n");
        System.out.print("Чтобы создать сессию нажмите " + 2 + "\n");System.out.print("Выберите игровую сессию\n");
        System.out.print("Выберите следующее действие:\n");
        System.out.print("Чтобы подключиться к существующей сессии нажмите " + 1 + "\n");
        System.out.print("Чтобы создать сессию нажмите " + 2 + "\n");
    }

    public static void makeMove() {
        //обработка валидатором
        //перевести в дто и в json и отправить в writer.println()
        display.showMoveRules();
    }

    public static void skipTurn() {
        //json отправить в writer
    }

    public static boolean enterServer() {
        display.showLoginActions();
        String message = null;
        try {
            LoginPassword lp = decisionMaker.getLoginPassword();
            switch (lp.type()) {
                case REGISTRATION ->
                        message = converter.getJsonFromObject(new RegistrationDtoRequest(lp.type(), lp.login(), lp.password()));
                case LOGIN ->
                        message = converter.getJsonFromObject(new LoginDtoRequest(lp.type(), lp.login(), lp.password()));
            }
            writer.writeUTF(message);
            writer.flush();
            LoginDtoResponse loginAnswer = converter.getObjectFromJson(reader.readUTF(), LoginDtoResponse.class);
            if (loginAnswer.status().equals("success")) {
                token = loginAnswer.token();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void joinGame() {
        //здесь надо отправить запрос на подключение к игре с gameId
        display.showRoomActions();
    }
}
