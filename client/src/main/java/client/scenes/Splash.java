package client.scenes;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

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
//        File qImgFile = new File("./client/src/main/resources/images/quizz.jpg");
//        try {
//            System.out.println(qImgFile.getCanonicalPath());
//            quizImage.setImage(new Image(qImgFile.getCanonicalPath()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
