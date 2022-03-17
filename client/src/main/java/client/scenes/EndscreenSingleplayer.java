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

    private SinglePlayer singlePlayerCtrl;

    @FXML private Text finalScoreTextBox;
    @FXML private Text correctAnswersTextBox;

    @Inject
    public EndscreenSingleplayer(StatSharerSingleplayer statSharer, MainCtrl mainCtrl, SinglePlayer singlePlayerCtrl) {
        this.statSharer = statSharer;
        this.mainCtrl = mainCtrl;
        this.singlePlayerCtrl = singlePlayerCtrl;
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){
        this.finalScoreTextBox.setText("Points: " + statSharer.points);
        this.correctAnswersTextBox.setText("Questions Answered: " + statSharer.correctAnswers + "/" + statSharer.totalQuestions);
    }

    public void switchToSinglePlayer(ActionEvent event) throws IOException {
        mainCtrl.switchToSinglePlayer();
    }

    public void switchToSplash(ActionEvent event) throws IOException{
        mainCtrl.switchToSplash();
    }
}
