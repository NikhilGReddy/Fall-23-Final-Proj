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
    FXMLLoader fxmlLoader = new FXMLLoader();
    Stage stage;
    Parent root;
    Scene scene;

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
    void logUserIn(ActionEvent event) throws IOException {
        System.out.println("reached");
        String inputtedName = userTextField.getText();
        String passWord = passWordTextField.getText();
        String encryptedPassword = EncryptorDecryptor.encrypt(passWord);
        Message userPassMsg = new Message("login", inputtedName, encryptedPassword);
        client.sendToServer(userPassMsg);
        System.out.println("reached -1 ");
        while(loginStatus ==0){
            int x =1;
        }
        System.out.println("reached 0 ");
        if(loginStatus ==1) {

            System.out.println("reached 1 ");
            root = fxmlLoader.load(getClass().getResource("MainMenu.fxml").openStream());
            System.out.println("reached 2 ");
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            System.out.println("reached 3 ");
            scene = new Scene(root);
            System.out.println("reached 4 ");

            stage.setScene(scene);
            System.out.println("reached 5 ");
            Message newMsg = new Message("initializeLanding");
            stage.show();
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
