package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class SinglePlayer implements Initializable {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Stage primaryStage;

    private Scene overview;

    @FXML
    private ProgressBar timerBar;

    double progress;

    @Inject
    public SinglePlayer(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){
        timerBar.setStyle("-fx-accent: red");
        timerBar.setProgress(50);

    }

    public void increaseProgress(){

        timerBar.setStyle("-fx-accent: red");

    }



}
