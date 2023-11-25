package ClientSide;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class MainPageController {
    @FXML
    private static ImageView ItemPic1;

    @FXML
    private static ImageView ItemPic2;

    @FXML
    private static ImageView ItemPic3;

    @FXML
    private  static ImageView ItemPic4;

    @FXML
    private static ImageView ItemPic5;

    public static void initializeMainMenu(Message message){
        Image i1 = new Image(message.imgURL[0]);
        ItemPic1 = new ImageView(i1);

        Image i2 = new Image(message.imgURL[1]);
        ItemPic2 = new ImageView(i2);

        Image i3 = new Image(message.imgURL[2]);
        ItemPic3 = new ImageView(i3);

        Image i4 = new Image(message.imgURL[3]);
        ItemPic4 = new ImageView(i4);

        Image i5 = new Image(message.imgURL[4]);
        ItemPic5 = new ImageView(i5);

    }

    @FXML
    private GridPane MainMenuGrid;
}
