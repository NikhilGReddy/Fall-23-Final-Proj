package ClientSide;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import com.google.gson.Gson;
import com.sun.tools.javac.Main;
import javafx.application.Platform;
import javafx.concurrent.Task;
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

import javax.accessibility.AccessibleIcon;

public class ItemController  extends Observable {
    public String bids;

    Gson gson = new Gson();
    public Parent root;
    public Scene scene;
    public Stage stage;
    public String name;
    private String description;
    private double curPrice;

    private double buyPrice;
    public String imgURL;
    public boolean doneBidProcessing = false;

    DecimalFormat df = new DecimalFormat("#.00");

    private boolean sold;
    MainMenuController parentController;
    ClientController parentParentController;
    private int itemNum;
    private boolean bidUpdate = false;
    private Client client;


    public boolean itemInfoReceived;
    @FXML private TextField bidAmt;
    @FXML private Button buyNowButton;
    @FXML public Text currentPrice;
    @FXML private Text buyNowPrice;
    @FXML private Text ItemName;
    @FXML private Text itemDescription;
    @FXML private ImageView ItemPic;
    @FXML public Text bidHistory;
    @FXML public Text minAcceptPrice;
    @FXML private Button bidButton;





    void init(int itemNum ,Parent root, Scene scene, Stage stage, String name, String description, double curPrice, double buyPrice,String imgURL, ClientController parentParentController, MainMenuController parentController, String bids){
        this.itemInfoReceived = false;
        this.itemNum = itemNum;
        this.sold = false;
        this.root = root;
        this.scene = scene;
        this.stage = stage;
        this.name = name;
        this.description = description;
        this.curPrice = curPrice;
        this.buyPrice = buyPrice;
        this.imgURL = imgURL;
        this.parentController = parentController;
        this.client = parentController.client;
        this.parentParentController = parentParentController;
        client.sendToServer(new Message("getLatestInfo", itemNum));
        int k =0;
        while(!itemInfoReceived){
            k++;
            if(k%10000==0)System.out.print(k/10000);
        }
        itemInfoReceived= false;


        ItemName.setText(this.name);
        itemDescription.setText(description);
        ItemPic.setImage(new Image(imgURL));
        currentPrice.setText("Current Price: " + df.format(this.curPrice));
        buyNowPrice.setText("Buy Now Price: "+ df.format(buyPrice));
        minAcceptPrice.setText("Min Bid Amount: " + (df.format(this.curPrice+0.01)));
        System.out.print(this.bids);
        bidHistory.setText(this.bids);
        if(this.name.contains("SOLD")){
            bidButton.setVisible(false);
            bidAmt.setVisible(false);
            buyNowButton.setVisible(false);
        }

    }
    public void quitItemViewer(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        MainMenuController controller = fxmlLoader.getController();
        Client.mainMenuController = controller;
        controller.init(root,stage,scene,parentController.getUser(),1,true,client, parentParentController);
        stage.setScene(scene);
        stage.show();
    }

    public void setBids(String bids){
        this.bids = bids;
    }



    public void placeBid(ActionEvent actionEvent) throws IOException {
        String bid = bidAmt.getText();
        double b = 0;
        try{
            b = Double.parseDouble(bid);
        } catch (NumberFormatException e){
            showInvalidBidAlert();
            return;
        }

        if(b <=curPrice){
            tooLowBidAlert();
            return;
        }

        Client.toServer.println(gson.toJson(new Message("Bid", itemNum,b,parentController.getUser()) ));
        Client.toServer.flush();

        doneBidProcessing = false;
        //updateBidinfo();

        bidUpdate = false;

        System.out.println("Bid updated");

        //Go back to MainMenu
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        MainMenuController controller = fxmlLoader.getController();
        Client.mainMenuController = controller;
        controller.init(root,stage,scene,parentController.getUser(),1,true,client, parentParentController);
        if(b >= this.buyPrice){
            showItemSoldAlert();
        } else {
            showBidAcceptedAlert();
        }
        stage.setScene(scene);
        stage.show();
    }


    public void setCurPrice(double price){
        this.curPrice = price;
    }



    public void buyNow(ActionEvent actionEvent) throws IOException {
        double b = this.buyPrice;
        Client.toServer.println(gson.toJson(new Message("Bid", itemNum,b,parentController.getUser()) ));
        Client.toServer.flush();
        doneBidProcessing = false;
        bidUpdate = false;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        MainMenuController controller = fxmlLoader.getController();
        Client.mainMenuController = controller;
        controller.init(root,stage,scene,parentController.getUser(),1,true,client, parentParentController);
        if(b >= this.buyPrice){
            showItemSoldAlert();
        } else {
            showBidAcceptedAlert();
        }
        stage.setScene(scene);
        stage.show();
    }

    private void tooLowBidAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Invalid Login");
        alert.setHeaderText(null);
        alert.setContentText("Bid is too low. Please try again.");
        alert.showAndWait();
    }

    private void showInvalidBidAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Invalid Bid");
        alert.setHeaderText(null);
        alert.setContentText("Your bid was invalid.");
        alert.showAndWait();
    }

    private void showBidAcceptedAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("You Placed a Bid!");
        alert.setHeaderText(null);
        alert.setContentText("Your Bid was Accepted.");
        alert.showAndWait();
    }

    private void showItemSoldAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Congrats!");
        alert.setHeaderText(null);
        alert.setContentText("You bought the item");
        alert.showAndWait();
    }

    public int getItemNum(){
        return this.itemNum;
    }

    public String getItemName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public double getBuyPrice() {
        return this.buyPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void logUserOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        ClientController.loginStatus = 0;
        MainMenuController.loggedIn = false;
        stage.show();

    }
}
