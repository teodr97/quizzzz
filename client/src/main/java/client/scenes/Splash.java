package client.scenes;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Splash implements Initializable {

    private final MainCtrl mainCtrl;

    @FXML
    private ImageView quizImage;

    @Inject
    public Splash(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){
        File qImgFile = new File("./src/main/resources/images/quizz.jpg");
        try {
            quizImage.setImage(new Image(qImgFile.getCanonicalPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
