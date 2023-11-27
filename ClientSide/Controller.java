package ClientSide;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
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

    Map<String, List<String>> bidHistory = new HashMap<>();

    public static String[] imgURL;


    public static int loginStatus;

    public static Client client;


    public  String user;

    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));

    int currentItem;

    public static String[] items;

    public static double minItemBid;



    @FXML
    private GridPane MainMenuGrid;

    @FXML private TextArea BidText;
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

    @FXML private Text minAcceptPrice;
    @FXML private TextField bidAmt;

    @FXML private Text Item1;

    @FXML private Text Item2;

    @FXML private Text Item3;
    @FXML private Text Item4;
    @FXML private Text Item5;

    @FXML private FlowPane MainFLowPane;

    @FXML private FlowPane ItemFlowPane;

    @FXML private Text ItemName;

    @FXML private ImageView ItemPic;


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
            MainFLowPane = (FlowPane) root;

            Item1 = (Text) MainFLowPane.lookup("#Item1");
            Item2 = (Text) MainFLowPane.lookup("#Item2");
            Item3 = (Text) MainFLowPane.lookup("#Item3");
            Item4 = (Text) MainFLowPane.lookup("#Item4");
            Item5 = (Text) MainFLowPane.lookup("#Item5");
            ItemPic1 = (ImageView) MainFLowPane.lookup("#ItemPic1");
            ItemPic2 = (ImageView) MainFLowPane.lookup("#ItemPic2");
            ItemPic3 = (ImageView) MainFLowPane.lookup("#ItemPic3");
            ItemPic4 = (ImageView) MainFLowPane.lookup("#ItemPic4");
            ItemPic5 = (ImageView) MainFLowPane.lookup("#ItemPic5");
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);

            stage.setScene(scene);

// Pass the necessary parameters to the init method
            initMainPage(stage, scene, inputtedName);
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

    void initMainPage(Stage stage, Scene scene, String user) throws IOException {
        this.stage = stage;
        this.scene = scene;
        this.user = user;



        // Set the text of Item1
        MainFLowPane = loader.load();

        Item1.setText(" "+ items[0]);

        Item2.setText(" "+ items[1]);
        Item3.setText(" "+ items[2]);
        Item4.setText(" "+items[3]);
        Item5.setText(" "+items[4]);



        Image i1 = new Image(imgURL[0]);
        ItemPic1.setImage(i1);


        Image i2 = new Image(imgURL[1]);
        ItemPic2.setImage(i2);

        Image i3 = new Image(imgURL[2]);
        ItemPic3.setImage(i3);

        Image i4 = new Image(imgURL[3]);
        ItemPic4.setImage(i4);

        Image i5 = new Image(imgURL[4]);
        ItemPic5.setImage(i5);

    }


    public void viewItem1(ActionEvent actionEvent) throws IOException {
        minItemBid = 10.0;
        this.currentItem = 1;
        bidHistory.put(items[0], new ArrayList<>());
        bidHistory.get(items[0]).add("This is bid 1");
        bidHistory.get(items[0]).add("This is bid 2");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemDetailsBid.fxml"));
        root = loader.load();
        ItemFlowPane = (FlowPane) root;
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        bidAmt = (TextField) ItemFlowPane.lookup("#bidAmt");
        minAcceptPrice = (Text) ItemFlowPane.lookup("#minAcceptPrice");
        double x =10.0;
        minAcceptPrice.setText(minAcceptPrice.getText()+" "+ x);
        stage.setScene(scene);
        ItemName = (Text)ItemFlowPane.lookup("#ItemName");
        ItemName.setText(items[0]);
        ItemPic = (ImageView) ItemFlowPane.lookup("#ItemPic");
        ItemPic.setImage(new Image(imgURL[0]));
        BidText = (TextArea) ItemFlowPane.lookup("#BidText");

        String bids = "";
        for(String c : bidHistory.get(items[0])){
            bids += c;
            bids+= "\n";
        }
        BidText.setText(bids);
        stage.show();

    }

    public void viewItem2(ActionEvent actionEvent) {
    }

    public void viewItem3(ActionEvent actionEvent) {
    }

    public void viewItem4(ActionEvent actionEvent) {
    }

    public void viewItem5(ActionEvent actionEvent) {
    }

    public void quit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void placeBid(ActionEvent actionEvent) {

        String b = bidAmt.getText();
        double b1 = 0;
        try {
            b1 = Double.valueOf(b);

            if(b1<minItemBid){
                Alert invalidBid = new Alert(AlertType.ERROR);
                invalidBid.setTitle("Bid Amount too low");
                invalidBid.setHeaderText("Please increase your bid");
                Button okButton = (Button)invalidBid.getDialogPane().lookupButton( ButtonType.OK );
                okButton.setText("try again!");
                invalidBid.showAndWait();
                return;
            }
            toServer.println(gson.toJson(new Message("Bid", this.currentItem,b1, this.user )));
        } catch (Exception e){
            Alert invalidBid = new Alert(AlertType.ERROR);
            invalidBid.setTitle("Bid Format Failed");
            invalidBid.setHeaderText("Please try again with a new bid");
            Button okButton = (Button)invalidBid.getDialogPane().lookupButton( ButtonType.OK );
            okButton.setText("try again!");
            invalidBid.showAndWait();
        }

    }

    public void quitItemViewer(ActionEvent actionEvent) {
    }
}
