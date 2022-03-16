package client.scenes;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Splash implements Initializable {

    private final MainCtrl mainCtrl;

    private Stage primaryStage;

    private Scene overview;

    @Inject
    public Splash(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }

    public void switchToPastGames(ActionEvent event) throws IOException {
        mainCtrl.switchToPastGames();
    }

    public void switchToSinglePlayer(ActionEvent event) throws IOException {
        mainCtrl.switchToSinglePlayer();
    }

    public void switchToUsername(ActionEvent event) throws IOException {
        mainCtrl.switchToUsername();
    }

    public void switchToHowToPlay(ActionEvent event) throws IOException {
        mainCtrl.switchToHowToPlay();
    }

}
