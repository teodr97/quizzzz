package client.scenes;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Splash implements Initializable {

    private final MainCtrl mainCtrl;

    @Inject
    public Splash(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }

    /**
     * Switches the scene to Past Games.
     * @param event
     * @throws IOException
     */
    public void switchToPastGames(ActionEvent event) throws IOException {
        mainCtrl.switchToPastGames();
    }

    /**
     * Switches the scene to Single Player.
     * @param event
     * @throws IOException
     */
    public void switchToSinglePlayer(ActionEvent event) throws IOException {
        mainCtrl.switchToSinglePlayer();
    }

    /**
     * Switches the scene to Username.
     * @param event
     * @throws IOException
     */
    public void switchToUsername(ActionEvent event) throws IOException {
        mainCtrl.switchToUsername();
    }

    /**
     * Switches the scene to How To Play.
     * @param event
     * @throws IOException
     */
    public void switchToHowToPlay(ActionEvent event) throws IOException {
        mainCtrl.switchToHowToPlay();
    }

}
