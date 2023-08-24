package io.deeplay.intership.client;

import io.deeplay.intership.decision.maker.*;
import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.dto.request.LoginDtoRequest;
import io.deeplay.intership.dto.request.RegistrationDtoRequest;
import io.deeplay.intership.dto.response.LoginDtoResponse;
import io.deeplay.intership.model.*;
import io.deeplay.intership.ui.terminal.Display;
import io.deeplay.intership.ui.terminal.UserInterface;
import io.deeplay.intership.json.converter.JSONConverter;

import java.io.*;
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

        try {
            init();
            while (enterServer()){

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void init() throws IOException {
        FileInputStream fis;
        Properties property = new Properties();
        boolean isGUI;
        fis = new FileInputStream("src/main/resources/config.properties");
        property.load(fis);
        host = property.getProperty("client.host");
        port = Integer.parseInt(property.getProperty("client.port"));
        isGUI = Boolean.parseBoolean(property.getProperty("client.GUI"));
        fis.close();
        socket = new Socket(host,port);
        reader = new DataInputStream(socket.getInputStream());
        writer = new DataOutputStream(socket.getOutputStream());
        converter = new JSONConverter();
        if(isGUI){
            //конструктор для гуи и дм
        } else {
            display = new Display();
            decisionMaker = new DecisionMakerTerminal();
        }
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
    public static boolean enterServer() throws IOException {
        display.showLoginActions();
        display.showLogin();
        display.showRegistration();
        LoginPassword lp = decisionMaker.getLoginPassword();
        String message = null;
        switch (lp.type()){
            case REGISTRATION -> message = converter.getJsonFromObject(new RegistrationDtoRequest(lp.type(), lp.login(), lp.password()));
            case LOGIN -> message = converter.getJsonFromObject(new LoginDtoRequest(lp.type(), lp.login(), lp.password()));
        }
        writer.writeUTF(message);
        writer.flush();
        try {
            LoginDtoResponse loginAnswer = converter.getObjectFromJson(reader.readUTF(), LoginDtoResponse.class);
            if (loginAnswer.status().equals("success")) {
                token = loginAnswer.token();
                return true;
            } else {
                return false;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
    public void joinGame(){

        //здесь надо отправить запрос на подключение к игре с gameId
    }
}
