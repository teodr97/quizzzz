package client.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Username implements Initializable {

    private MainCtrl mainCtrl;

    @FXML
    private TextField username;

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

    /**
     * Switches to the waiting room screen.
     * @param event
     */
    public void switchToWaitingRoom(ActionEvent event) {
        mainCtrl.switchToWaitingRoom();
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return this.username.getText();
    }
}
