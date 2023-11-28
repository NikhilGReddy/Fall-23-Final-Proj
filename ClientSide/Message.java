package ClientSide;

import java.io.Serializable;

public class Message implements Serializable {
    double[] buyNowPrices;
    double[] curPrices;
    String type;
    String input;
    int number;
    int itemNum;
    double bidAmount;
    String bidUser;

    String[] imgURL;

    int[] timeLeft;
    String[] itemName;
    String bids;

    String name;
    double newMax;
    String password;
    double curMax;
    double buyNowPrice;
    String[] descriptions;
    protected Message() {
        this.type = "";
        this.input = "";
        this.number = 0;
        System.out.println("server-side message created");
    }

    protected Message(String type, String bids, double newMax){
        this.type = type;
        this.bids = bids;
        this.newMax = newMax;
    }



    protected Message(String type, String name, String password){
        this.type = type;
        this.name = name;
        this.password = password;
    }

    protected Message(String type,String[] imgURL, int[] timeLeft, String[] itemName, String[] descriptions, double[] curPrices, double[] buyNowPrices){
        this.type = type;
        this.imgURL =imgURL;
        this.timeLeft = timeLeft;
        this.itemName = itemName;
        this.descriptions = descriptions;
        this.curPrices = curPrices;
        this.buyNowPrices = buyNowPrices;
    }

    protected Message(String type, int itemNum, double bidAmount, String bidUser){
        this.type = type;
        this.itemNum = itemNum;
        this.bidAmount = bidAmount;
        this.bidUser = bidUser;
    }

    protected Message(String type, String input, int number) {
        this.type = type;
        this.input = input;
        this.number = number;
        System.out.println("server-side message created");
    }





    protected Message(String type){
        this.type = type;
    }
}
