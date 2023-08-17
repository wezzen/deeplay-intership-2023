package io.deeplay.intership.client;

import java.io.*;
import java.net.Socket;
import java.util.Properties;
public class Client {
    private String host;
    private int port;
    public Client() throws IOException {
        FileInputStream fis;
        Properties property = new Properties();
        fis = new FileInputStream("src/main/resources/config.properties");
        property.load(fis);
        this.host = property.getProperty("client.host");
        this.port = Integer.parseInt(property.getProperty("client.port"));
        fis.close();
    }

    public Client(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void clientProcess(){
        try(Socket socket = new Socket(host,port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);) {
            boolean isConnected = true;
            String fromServer, toServer = "request";
            while (isConnected){
                out.println(toServer);
                fromServer = in.readLine();
                //
                // Код клиента - обработка
                //
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
