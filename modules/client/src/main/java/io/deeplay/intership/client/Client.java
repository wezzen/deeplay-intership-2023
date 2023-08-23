package io.deeplay.intership.client;

import io.deeplay.intership.decision.maker.DecisionMakerTerminal;
import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.model.*;
import io.deeplay.intership.ui.terminal.Display;

import java.io.*;
import java.net.Socket;
import java.util.Properties;
public class Client {
    private static String host;
    private static int port;
    private static Socket socket;
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static Display display;
    private static DecisionMakerTerminal decisionMaker;

    public static void main(String[] args) {
        try {
            init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void init() throws IOException {
        FileInputStream fis;
        Properties property = new Properties();
        fis = new FileInputStream("src/main/resources/config.properties");
        property.load(fis);
        host = property.getProperty("client.host");
        port = Integer.parseInt(property.getProperty("client.port"));
        fis.close();
        socket = new Socket(host,port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(),true);
        display = new Display();
    }


    public static void sendRequest(RequestType request){

    }
    public static void makeMove(Stone stone){
        //обработка валидатором
        //перевести в дто и в json и отправить в writer.println()
    }
    public static void skipTurn(){
        //json отправить в writer
    }
    public static void enterGame(RequestType requestType, String login, String pswd){
        //выбрать json и отправить
    }
    public void joinGame(int gameId){
        //здесь надо отправить запрос на подключение к игре с gameId
    }
    public int createGame(){
        //здесь надо отправить запрос на создание игры и вернуть gameId
        int gameId = 1;
        return gameId;
    }
}
