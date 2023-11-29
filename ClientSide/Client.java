package ClientSide;
import java.net.http.WebSocket;
import java.util.*;
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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import javafx.util.Duration;

public class Client extends Application  {

    private static String host = "127.0.0.1";
    private BufferedReader fromServer;
    public static PrintWriter toServer;
    private Scanner consoleInput = new Scanner(System.in);

    public static Gson gson = new Gson();

    static ClientController clientController;
    static MainMenuController mainMenuController;




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
        Scene scene  = new Scene(root, 400, 400);
        clientController = fxmlLoader.getController();
        ClientController.client  =this;
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setUpNetworking() throws Exception {
        @SuppressWarnings("resource")
        Socket socket = new Socket(host, 4242);
        System.out.println("Connecting to... " + socket);
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toServer = new PrintWriter(socket.getOutputStream());
        Message newMsg = new Message("initializeLanding");
        sendToServer(newMsg);


        Thread readerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String input;
                try {
                    while ((input = fromServer.readLine()) != null) {

                        Message message = gson.fromJson(input, Message.class);
                        processRequest(message);
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
                    sendToServer(request);
                }
            }
        });

        readerThread.start();
        writerThread.start();
    }


    protected void processRequest(Message message) {
        try{
            switch (message.type){
                case "loggedIn":
                    clientController.loginStatus = 1;
                    System.out.println("server received and returned");
                    break;
                case "invalidLogin":
                    clientController.loginStatus = -1;
                    break;
                case "landingInfo":
                    mainMenuController.imgUrls = message.imgURL;
                    mainMenuController.itemDescriptions = message.descriptions;
                    mainMenuController.itemPrices = message.curPrices;
                    mainMenuController.items = message.itemName;
                    mainMenuController.buyNowPrices = message.buyNowPrices;
                    mainMenuController.initLanding = true;
                    break;
                case "itemInit":
                    mainMenuController.controller.setBids( message.bids);
                    mainMenuController.controller.setCurPrice(message.curMax);
                    mainMenuController.controller.setName(message.itemName1);
                    mainMenuController.controller.itemInfoReceived = true;
                    System.out.println("7  " + message.bids);
                    break;
                default:
                    //System.out.println("error has occured in processing your request");
            }
        } catch ( Exception e){
            System.out.println();
        }
    }


    protected void sendToServer(Message message) {
        System.out.println("Sending to server: ");
        toServer.println(gson.toJson(message));
        toServer.flush();
    }



}
