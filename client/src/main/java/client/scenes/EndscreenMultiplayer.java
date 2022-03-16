package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class EndscreenMultiplayer implements Initializable {

    private final ServerUtils server;
    private final MainCtrlQuotes mainCtrl;

    private Stage primaryStage;

    private Scene overview;

    @Inject
    public EndscreenMultiplayer(ServerUtils server, MainCtrlQuotes mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }
}
