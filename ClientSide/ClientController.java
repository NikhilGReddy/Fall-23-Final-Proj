package ClientSide;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class ClientController {

    public static int loginStatus;

    public static Client client;

    @FXML
    private ImageView ItemPic1;

    @FXML
    private ImageView ItemPic2;

    @FXML
    private ImageView ItemPic3;

    @FXML
    private ImageView ItemPic4;

    @FXML
    private ImageView ItemPic5;
    @FXML
    private GridPane MainMenuGrid;

    @FXML
    private  TextField userTextField;

    @FXML
    private PasswordField passWordTextField;

    @FXML
    void logUserIn() throws IOException {
        String inputtedName = userTextField.getText();
        String passWord = passWordTextField.getText();
        String encryptedPassword = EncryptorDecryptor.encrypt(passWord);
        Message userPassMsg = new Message("login", inputtedName, encryptedPassword);
        client.sendToServer(userPassMsg);
        while(loginStatus==0);
        if(loginStatus ==1) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Stage primaryStage = new Stage();
            Parent root = fxmlLoader.load(getClass().getResource("MainMenu.fxml"));
            Scene scene = new Scene(root, 400, 400);


            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            loginStatus = 0;
            Alert invalidLogin = new Alert(AlertType.ERROR);
            invalidLogin.setTitle("Login Failed");
            invalidLogin.setHeaderText("Please try again with a new username/password");
            Button okButton = (Button)invalidLogin.getDialogPane().lookupButton( ButtonType.OK );
            okButton.setText("try again!");
            invalidLogin.showAndWait();
        }

    }




}
