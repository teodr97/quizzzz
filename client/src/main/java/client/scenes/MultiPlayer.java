package client.scenes;

import commons.models.Game;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MultiPlayer implements Initializable {

    private Game game;
    private MainCtrl mainCtrl;

    private final Image reactionAngry = new Image("file:///D:/Projects/Git/repository-template/client/resources/images/reactLol.png");

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

    @FXML
    private ListView<AnchorPane> listViewReactions;

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
        displayReaction("TEst", reactionAngry);
    }

    /**
     * switches to the splash screen, for the leave button
     * @param event
     * @throws IOException
     */
    public void switchToSplash(ActionEvent event) throws IOException {
        mainCtrl.switchToSplash();
    }

    public void displayReaction(String username, Image reaction) {
        ImageView reactionImg = new ImageView(reaction);
        Text usernameText = new Text(username);
        AnchorPane panel = new AnchorPane();

        //usernameText.setX(400);
        usernameText.setY(30);
        panel.getChildren().add(reactionImg);
        panel.getChildren().add(usernameText);
        reactionImg.setFitHeight(40);
        reactionImg.setFitWidth(40);
        reactionImg.setX(175);
        reactionImg.setY(5);

        listViewReactions.setItems(FXCollections.observableArrayList(panel));
    }
}
