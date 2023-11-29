package ClientSide;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.javafx.PlatformUtil;
import com.sun.media.jfxmedia.locator.Locator;
import com.google.gson.Gson;
import com.sun.tools.javac.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;

import static ClientSide.Client.toServer;


public class ClientController {
    private Parent root;
    private Stage stage;
    private Scene scene;
    private static String user;
    private static int itemNum;
    private static boolean loggedIn;
    public static int loginStatus;
    public static Client client;

    private Gson gson = new Gson();

    @FXML
    public void quitLogin(ActionEvent event){
        System.exit(0);
    }

    @FXML
    private TextField userTextField;

    @FXML
    private PasswordField passWordTextField;
    @FXML
    public void logUserIn(ActionEvent event) throws IOException {
        //Create message to server with proper login info
        Message loginMessage = new Message();
        loginMessage.type = "login";
        loginMessage.name = userTextField.getText();
        loginMessage.password = EncryptorDecryptor.encrypt(passWordTextField.getText());

        //Send message to server
        toServer.println(gson.toJson(loginMessage));
        toServer.flush();
        int k =0;
        //BusyWait for loginstatus
        while(loginStatus==0){
            k++;
            if(k%500 ==0){
                System.out.print(k);
            }
            if(loginStatus == 1)break;

        }
        System.out.println("reached between");
        if(loginStatus>0){
            ClientController.user = userTextField.getText();
            ClientController.loggedIn = true;
            //Replace login window with MainMenu Window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            root = fxmlLoader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            MainMenuController controller = fxmlLoader.getController();
            Client.mainMenuController = controller;
            controller.init(root,stage,scene,user,loginStatus,loggedIn,client, this);
            String mediaFile = getClass().getResource("BackgroundMusic.mp3").toExternalForm();
            Media audio = new Media(mediaFile);
            MediaPlayer mediaPlayerBG = new MediaPlayer(audio);
            mediaPlayerBG.setVolume(0.05);
            mediaPlayerBG.play();
            stage.setScene(scene);
            stage.show();
        } else {
            loggedIn = false;
            loginStatus = 0;
            showInvalidLoginAlert();
        }
    }




    private void showInvalidLoginAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Invalid Login");
        alert.setHeaderText(null);
        alert.setContentText("Invalid username or password. Please try again.");
        alert.showAndWait();
    }

}
