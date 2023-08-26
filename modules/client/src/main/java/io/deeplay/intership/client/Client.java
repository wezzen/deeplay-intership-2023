package io.deeplay.intership.client;

import io.deeplay.intership.UserInterface;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.DecisionMakerTerminal;
import io.deeplay.intership.json.converter.JSONConverter;
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

    public static void main(String[] args) {
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
            //конструктор для гуи и дм
        } else {
            display = new Display();
            decisionMaker = new DecisionMakerTerminal(new Scanner(System.in));
        }
    }

    public static void sendRequest(String request) {
        try {
            writer.writeUTF(request);
            writer.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
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
        return false;
    }

    public static void joinGame() {
        //здесь надо отправить запрос на подключение к игре с gameId
        display.showRoomActions();
    }
}
