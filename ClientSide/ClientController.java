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
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class ClientController {

    @FXML
    private static TextField userTextField;

    @FXML
    private static PasswordField passWordTextField;

    @FXML
    static void logUserIn(){
        String inputtedName = userTextField.getText();
        String passWord = passWordTextField.getText();
        String encryptedPassword = EncryptorDecryptor.encrypt(passWord);
        String userPassMsd = "Username: " + inputtedName + " Password: " + encryptedPassword;
        System.out.println(userPassMsd);
        System.out.println(passWord);
        Client.sendToServer(userPassMsd);
    }

    public static Client client;
}
