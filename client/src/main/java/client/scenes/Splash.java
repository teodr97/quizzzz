package client.scenes;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;


public class Splash implements Initializable {

    private final MainCtrl mainCtrl;

    @FXML
    private ImageView quizImage;

    @Inject
    public Splash(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources){
        Path path = Paths.get("src", "main","resources","images","quizz.jpg");
        quizImage.setImage(new Image(path.toUri().toString()));
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
     * Switches the scene to Single Player Username.
     * @param event
     * @throws IOException
     */
    public void switchToSingleplayerUsername(ActionEvent event) throws IOException {
        mainCtrl.switchToSingleplayerUsername();
    }

    /**
     * Switches the scene to the Multiplayer game screen. FOR DEBUG PURPOSES ONLY
     * @param event
     * @throws IOException
     */
    public void switchToMultiPlayer(ActionEvent event) throws IOException {
        mainCtrl.switchToMultiplayer(); //FOR DEBUG ONLY
    }

    /**
     * Switches the scene to Username.
     * @param event
     * @throws IOException
     */
    public void switchToUsername(ActionEvent event) throws IOException {
        mainCtrl.startUserLongPolling();
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

    /**
     * Switches the scene to Admin panel.
     * @param event
     * @throws IOException
     */
    public void switchToAdmin(ActionEvent event) throws IOException {
        mainCtrl.switchToAdmin();
    }

    /**
     * Switches the scene to the Server Selection screen.
     * @param event The button click event.
     */
    public void switchToServerSelection(ActionEvent event) {
        mainCtrl.switchToServerSelection();
    }

}
