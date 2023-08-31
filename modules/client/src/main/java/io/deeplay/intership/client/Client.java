package io.deeplay.intership.client;

import io.deeplay.intership.UserInterface;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.decision.maker.terminal.DecisionMakerTerminal;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.ui.terminal.Display;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class Client {
    private static final String CONFIG_PATH = "src/main/resources/config.properties";
    private static String host;
    private static int port;
    private static Socket socket;
    private static DataInputStream reader;
    private static DataOutputStream writer;
    private static UserInterface display;
    private static DecisionMaker decisionMaker;
    private static StreamConnector streamConnector;
    private static String token;
    private static GameController gameController;
    private static AuthorizationController authorizationController;

    public static void main(String[] args) {
        init();

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
            streamConnector = new StreamConnector(writer, reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isGUI) {
            // ГУИ + ДМ
        } else {
            display = new Display();
            decisionMaker = new DecisionMakerTerminal(new Scanner(System.in));
        }
        gameController = new GameController(streamConnector, display, decisionMaker);
    }

    private static boolean isSuccessful(BaseDtoResponse dtoResponse) {
        if (dtoResponse.status.equals("success")) {
            return true;
        }
        return false;
    }
}
