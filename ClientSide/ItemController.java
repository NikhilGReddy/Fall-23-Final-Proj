package ClientSide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class ItemController {
    public String bids;
    Gson gson = new Gson();
    private Parent root;
    private Scene scene;
    private Stage stage;
    private String name;
    private String description;
    private double curPrice;

    private double buyPrice;
    private String imgURL;
    public boolean doneBidProcessing = false;


    private boolean sold;
    private MainMenuController parentController;
    private ClientController parentParentController;
    private int itemNum;
    private boolean bidUpdate = false;

    @FXML private TextField bidAmt;
    @FXML private Text currentPrice;
    @FXML private Text buyNowPrice;
    @FXML private Text ItemName;
    @FXML private Text itemDescription;
    @FXML private ImageView ItemPic;
    @FXML private Text bidList;
    @FXML private Text minAcceptPrice;





    void init(int itemNum ,Parent root, Scene scene, Stage stage, String name, String description, double curPrice, double buyPrice,String imgURL, ClientController parentParentController, MainMenuController parentController){
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
        this.parentParentController = parentParentController;
        this.bids = "";
        ItemName.setText(name);
        itemDescription.setText(description);
        ItemPic.setImage(new Image(imgURL));
        currentPrice.setText("Current Price: " + curPrice);
        buyNowPrice.setText("Buy Now Price: "+ buyPrice);
        minAcceptPrice.setText("Min Bid Amount: " + (curPrice+0.01));
    }
    public void quitItemViewer(ActionEvent actionEvent) {

    }

    public void placeBid(ActionEvent actionEvent) {
        String bid = bidAmt.getText();
        double b = 0;
        try{
            b = Double.parseDouble(bid);
        } catch (NumberFormatException e){
            showInvalidBidAlert();
            return;
        }

        if(b <=curPrice+0.01){
            tooLowBidAlert();
            return;
        }

        Client.toServer.println(gson.toJson(new Message("Bid", itemNum,b,parentController.getUser()) ));
        Client.toServer.flush();
        while(!doneBidProcessing){
            int x  =1;
        }
        doneBidProcessing = false;


        System.out.println("Bid updated");


    }



    public void setCurPrice(double price){
        this.curPrice = price;
    }



    public void buyNow(){
        sold = true;
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

    class UpDateBidList implements Runnable{


        @Override
        public void run(){
            bidList.setText(bids);
            currentPrice.setText("Current Price: " + curPrice);
            minAcceptPrice.setText("Min Bid Amount: " + (curPrice+0.01));
        }
    }
}
