package client.scenes;

import commons.models.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class SingleplayerUsername implements Initializable {

    private MainCtrl mainCtrl;
    private Player player;

    @FXML
    private TextField username;

    @FXML
    private Text missingUser;

    @Inject
    public SingleplayerUsername(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public SingleplayerUsername() {

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
     * switches to singleplayer game screen and starts the game
     * @param event
     * @throws IOException
     */
    public void startGame(ActionEvent event) throws IOException {
        if(username.getText() == null || username.getText().equals("")){
            missingUser.setText("please enter a username");
        }else {
            player = new Player(username.getText());
            SinglePlayer s = new SinglePlayer();
            s.setUsername(player.getNickname());
            mainCtrl.switchToSinglePlayer();
        }
    }

    /**
     * Returns the username of the player.
     * @return
     */
    public String getUsername() {
        return player.getNickname();
    }
}
