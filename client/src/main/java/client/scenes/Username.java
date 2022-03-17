package client.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Username implements Initializable {

    private MainCtrl mainCtrl;

    @Inject
    public Username(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }

    /**
     * switches to the splash screen, for the leave button
     * @param event
     * @throws IOException
     */
    public void switchToSplash(ActionEvent event) throws IOException {
        mainCtrl.switchToSplash();
    }

    /**
     * switches to the multiplayer screen, for the join game button
     * @param event
     * @throws IOException
     */
    public void switchToMultiplayer(ActionEvent event) throws IOException {
        mainCtrl.switchToMultiplayer();
    }


}
