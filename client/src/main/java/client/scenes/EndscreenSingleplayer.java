package client.scenes;

import client.utils.StatSharerSingleplayer;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class EndscreenSingleplayer implements Initializable {

    private final MainCtrl mainCtrl;

    /**
     * Used to share information between the game and the end-screen.
     * Here it is used to display the information.
     */
    private StatSharerSingleplayer statSharer;

    @FXML private Text finalScoreTextBox;
    @FXML private Text questionsAnswered;

    @Inject
    public EndscreenSingleplayer(StatSharerSingleplayer statSharer, MainCtrl mainCtrl) {
        this.statSharer = statSharer;
        this.mainCtrl = mainCtrl;
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){
        this.finalScoreTextBox.setText("Points: " + statSharer.points);
        this.questionsAnswered.setText("Correct Answers: " + statSharer.correctAnswers + "/" + statSharer.totalAnswers);
    }

    /**
     * Switches the scene to singleplayer.
     * @param event
     * @throws IOException
     */
    public void switchToSinglePlayer(ActionEvent event) throws IOException {
        mainCtrl.switchToSinglePlayer();
    }

    /**
     * Switches the scene to Splash.
     * @param event
     * @throws IOException
     */
    public void switchToSplash(ActionEvent event) throws IOException{
        mainCtrl.switchToSplash();
    }
}
