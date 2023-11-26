package ClientSide;
import java.io.IOException;

import com.google.gson.Gson;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static ClientSide.Client.toServer;


public class Controller {

    public static boolean doneInit =false;
    FXMLLoader fxmlLoader = new FXMLLoader();
    Stage stage;
    Parent root;
    Scene scene;
    Gson gson = new Gson();

    public static String[] imgURL;


    public static int loginStatus;

    public static Client client;


    public  String user;

    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));



    public static String[] items;

    @FXML
    private GridPane MainMenuGrid;
    @FXML
    private ImageView ItemPic1;

    @FXML
    private  ImageView ItemPic2;

    @FXML
    private  ImageView ItemPic3;

    @FXML
    private ImageView ItemPic4;

    @FXML
    private  ImageView ItemPic5;

    @FXML private Text Item1;

    @FXML private Text Item2;

    @FXML private Text Item3;
    @FXML private Text Item4;
    @FXML private Text Item5;





    @FXML
    private TextField userTextField;

    @FXML
    private PasswordField passWordTextField;

    @FXML
    void logUserIn(ActionEvent event) throws IOException {
        System.out.println("reached");
        String inputtedName = userTextField.getText();
        String passWord = passWordTextField.getText();
        String encryptedPassword = EncryptorDecryptor.encrypt(passWord);
        Client.toServer.println(gson.toJson(new Message("login", inputtedName, encryptedPassword)));
        toServer.flush();
        while(loginStatus ==0){
            int x =1;
        }

        if(loginStatus ==1) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            root = loader.load();
            MainMenuGrid = (GridPane) root;
            Item1 = (Text) MainMenuGrid.lookup("#Item1");
            Item2 = (Text) MainMenuGrid.lookup("#Item2");
            Item3 = (Text) MainMenuGrid.lookup("#Item3");
            Item4 = (Text) MainMenuGrid.lookup("#Item4");
            Item5 = (Text) MainMenuGrid.lookup("#Item5");
            ItemPic1 = (ImageView) MainMenuGrid.lookup("#ItemPic1");
            ItemPic2 = (ImageView) MainMenuGrid.lookup("#ItemPic2");
            ItemPic3 = (ImageView) MainMenuGrid.lookup("#ItemPic3");
            ItemPic4 = (ImageView) MainMenuGrid.lookup("#ItemPic4");
            ItemPic5 = (ImageView) MainMenuGrid.lookup("#ItemPic5");
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);

            stage.setScene(scene);

// Pass the necessary parameters to the init method
            init(stage, scene, inputtedName);
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

    void init(Stage stage, Scene scene, String user) throws IOException {
        this.stage = stage;
        this.scene = scene;
        this.user = user;



        // Set the text of Item1
        MainMenuGrid = loader.load();

        Item1.setText(items[0]);

        Item2.setText(items[1]);
        Item3.setText(items[2]);
        Item4.setText(items[3]);
        Item5.setText(items[4]);



        Image i1 = new Image(imgURL[0]);
        ItemPic1.setImage(i1);


        Image i2 = new Image(imgURL[1]);
        ItemPic2 = new ImageView(i2);

        Image i3 = new Image(imgURL[2]);
        ItemPic3 = new ImageView(i3);

        Image i4 = new Image(imgURL[3]);
        ItemPic4 = new ImageView(i4);

        Image i5 = new Image(imgURL[4]);
        ItemPic5 = new ImageView(i5);

    }




}
