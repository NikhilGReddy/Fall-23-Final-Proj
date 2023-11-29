package ClientSide;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;

public class MainMenuController {


    private Parent root;
    private Stage stage;
    private Scene scene;
    private static String user;
    public static String[] items;  //Hold item names
    public static int loginStatus;
    public static double[] buyNowPrices;
    public static boolean loggedIn;
    public static Client client;
    public static final DecimalFormat decFormat = new DecimalFormat("0.00");
    public boolean initLanding =false;
    public double[] itemPrices;
    public String[] itemDescriptions;
    public String[] imgUrls;


    ItemController controller;
    ClientController parentController;

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

    @FXML private Text Item1;
    @FXML private Text Item2;
    @FXML private Text Item3;
    @FXML private Text Item4;
    @FXML private Text Item5;
    @FXML private Text description1;
    @FXML private Text description2;
    @FXML private Text description3;
    @FXML private Text description4;
    @FXML private Text description5;
    @FXML private Text ItemPrice1;
    @FXML private Text ItemPrice2;
    @FXML private Text ItemPrice3;
    @FXML private Text ItemPrice4;
    @FXML private Text ItemPrice5;




    void init(Parent root, Stage stage, Scene scene, String user,  int loginStatus, boolean loggedIn, Client client, ClientController parentController){
        this.root = root;
        this.stage = stage;
        this.scene = scene;
        this.user = user;
        this.loginStatus = loginStatus;
        this.loggedIn = loggedIn;
        this.client = client;
        this.parentController = parentController;

        //Get data from server for image URLs, item names, item descriptions, and current item prices
        client.sendToServer(new Message("initializeLanding"));
        while(!initLanding){
            int x =1;
        }
        initLanding = false;
        description1.setText(itemDescriptions[0]);
        description2.setText(itemDescriptions[1]);
        description3.setText(itemDescriptions[2]);
        description4.setText(itemDescriptions[3]);
        description5.setText(itemDescriptions[4]);
        Item1.setText(items[0]);
        Item2.setText(items[1]);
        Item3.setText(items[2]);
        Item4.setText(items[3]);
        Item5.setText(items[4]);
        if(items[0].contains("SOLD")){
            ItemPrice1.setText("SOLD");
        } else{
            ItemPrice1.setText("Current Max Bid: " + itemPrices[0]);
        }
        if(items[1].contains("SOLD")){
            ItemPrice2.setText("SOLD");
        } else {
            ItemPrice2.setText("Current Max Bid: " + itemPrices[1]);
        }
        if(items[2].contains("SOLD")){
            ItemPrice3.setText("SOLD");
        } else {
            ItemPrice3.setText("Current Max Bid: " + itemPrices[2]);
        }
        if(items[3].contains("SOLD")){
            ItemPrice4.setText("SOLD");
        } else {
            ItemPrice4.setText("Current Max Bid: " + itemPrices[3]);
        }
        if(items[4].contains("SOLD")){
            ItemPrice5.setText("SOLD");
        } else {
            ItemPrice5.setText("Current Max Bid: " + itemPrices[4]);
        }


        ItemPic1.setImage(new Image(imgUrls[0]));
        ItemPic2.setImage(new Image(imgUrls[1]));
        ItemPic3.setImage(new Image(imgUrls[2]));
        ItemPic4.setImage(new Image(imgUrls[3]));
        ItemPic5.setImage(new Image(imgUrls[4]));
    }




    public void viewItem1(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ItemDetailsBid.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ItemController controller = fxmlLoader.getController();
        this.controller = controller;
        controller.init(0,root, scene, stage, items[0],itemDescriptions[0], itemPrices[0],buyNowPrices[0],imgUrls[0], parentController, this, "");
        stage.setScene(scene);
        stage.show();
    }

    public void viewItem2(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ItemDetailsBid.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ItemController controller = fxmlLoader.getController();
        this.controller = controller;
        controller.init(1,root, scene, stage, items[1],itemDescriptions[1], itemPrices[1],buyNowPrices[1],imgUrls[1], parentController, this, "");
        stage.setScene(scene);
        stage.show();
    }

    public void viewItem3(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ItemDetailsBid.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ItemController controller = fxmlLoader.getController();
        this.controller = controller;
        controller.init(2,root, scene, stage, items[2],itemDescriptions[2], itemPrices[2],buyNowPrices[2],imgUrls[2], parentController, this, "");
        stage.setScene(scene);
        stage.show();
    }

    public void viewItem4(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ItemDetailsBid.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ItemController controller = fxmlLoader.getController();
        this.controller = controller;
        controller.init(3,root, scene, stage, items[3],itemDescriptions[3], itemPrices[3],buyNowPrices[3],imgUrls[3], parentController, this, "");
        stage.setScene(scene);
        stage.show();
    }

    public void viewItem5(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ItemDetailsBid.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ItemController controller = fxmlLoader.getController();
        this.controller = controller;
        controller.init(4,root, scene, stage, items[4],itemDescriptions[4], itemPrices[4],buyNowPrices[4],imgUrls[4], parentController, this, "");
        stage.setScene(scene);
        stage.show();
    }

    public void quit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        ClientController.loginStatus = 0;
        MainMenuController.loggedIn = false;
        stage.show();
    }

    public String getUser(){
        return this.user;
    }
}
