package ClientSide;

public class Message {
    String type;
    String input;
    int number;

    String[] imgURL;

    int[] timeLeft;
    String[] itemName;

    String name;
    String password;

    protected Message() {
        this.type = "";
        this.input = "";
        this.number = 0;
        System.out.println("server-side message created");
    }

    protected Message(String type, String name, String password){
        this.type = type;
        this.name = name;
        this.password = password;
    }

    protected Message(String type,String[] imgURL, int[] timeLeft, String[] itemName){
        this.type = type;
        this.imgURL =imgURL; this.timeLeft = timeLeft; this.itemName = itemName;
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