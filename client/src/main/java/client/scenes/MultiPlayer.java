package client.scenes;


import client.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MultiPlayer implements Initializable {

    private Game game;
    private MainCtrl mainCtrl;

    @FXML
    private ImageView jokerHG;

    @FXML
    private ImageView joker2X;

    @FXML
    private ImageView jokerMB;

    @FXML
    private Button answerA;

    @FXML
    private Button answerB;

    @FXML
    private Button answerC;

    @FXML
    private Text prompt;

    @FXML
    private Text userpoint;
    private int pointsInt = 0;

    @FXML
    private Text questionField;

    @FXML
    private Text qNumber;
    //

    @Inject
    public MultiPlayer(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        /*
        File j2x = new File("./client/src/main/resources/images/Joker2X.png");
        File jHg = new File("./client/src/main/resources/images/JokerHG.png");
        File jMb = new File("./client/src/main/resources/images/JokerMB.png");
        try {
            joker2X.setImage(new Image(j2x.getCanonicalPath()));
            jokerHG.setImage(new Image(jHg.getCanonicalPath()));
            jokerMB.setImage(new Image(jMb.getCanonicalPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
    }

    /**
     * switches to the splash screen, for the leave button
     * @param event
     * @throws IOException
     */
    public void switchToSplash(ActionEvent event) throws IOException {
        mainCtrl.switchToSplash();
    }


}
