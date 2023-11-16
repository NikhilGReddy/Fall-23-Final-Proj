package ClientSide;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Client extends Application{

    private static String host = "127.0.0.1";
    private BufferedReader fromServer;
    private PrintWriter toServer;
    private Scanner consoleInput = new Scanner(System.in);




    public static void main(String[] args) {
        try {
            new Client().setUpNetworking();
        } catch (Exception e) {
            e.printStackTrace();
        }
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root,400, 401);
        Object clientController = fxmlLoader.getController();
        ClientController.client = this;
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setUpNetworking() throws Exception {
        @SuppressWarnings("resource")
        Socket socket = new Socket(host, 4242);
        System.out.println("Connecting to... " + socket);
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toServer = new PrintWriter(socket.getOutputStream());

        Thread readerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String input;
                try {
                    while ((input = fromServer.readLine()) != null) {
                        System.out.println("From server: " + input);
                        processRequest(input);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread writerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String input = consoleInput.nextLine();
                    String[] variables = input.split(",");
                    Message request = new Message(variables[0], variables[1], Integer.valueOf(variables[2]));
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    sendToServer(gson.toJson(request));
                }
            }
        });

        readerThread.start();
        writerThread.start();
    }


    protected void processRequest(String input) {
        return;
    }


    protected static void sendToServer(String string) {
        System.out.println("Sending to server: " + string);
        toServer.println(string);
        toServer.flush();
    }



}
