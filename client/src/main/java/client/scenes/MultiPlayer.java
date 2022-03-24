package client.scenes;

import client.utils.GuiUtils;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MultiPlayer implements Initializable {

    private Game game;
    private MainCtrl mainCtrl;

    private final Image reactionAngry = new Image(Paths.get("src", "main","resources","images","reactAngry.png").toUri().toString());
    private final Image reactionLol = new Image(Paths.get("src", "main","resources","images","reactLol.png").toUri().toString());
    private final Image reactionClap = new Image(Paths.get("src", "main","resources","images","reactClap.png").toUri().toString());
    private final Image reactionCool = new Image(Paths.get("src", "main","resources","images","reactCool.png").toUri().toString());
    private final Image reactionSweaty = new Image(Paths.get("src", "main","resources","images","reactSweaty.png").toUri().toString());

    @FXML private ImageView jokerHG;
    @FXML private ImageView joker2X;
    @FXML private ImageView jokerMB;
    @FXML private ImageView imgBttnReactLol;
    @FXML private ImageView imgBttnReactAngry;
    @FXML private ImageView imgBttnReactCool;
    @FXML private ImageView imgBttnReactClap;
    @FXML private ImageView imgBttnReactSweaty;

    @FXML private Button answerA;
    @FXML private Button answerB;
    @FXML private Button answerC;

    @FXML private Text prompt;
    @FXML private Text userpoint;

    private int pointsInt = 0;

    @FXML private Text questionField;
    @FXML private Text qNumber;

    @FXML private ListView<AnchorPane> listViewReactions;

    @Inject
    public MultiPlayer(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){

        Path hgPath = Paths.get("src", "main","resources","images","JokerHG.png");
        Path twoxPath = Paths.get("src", "main","resources","images","Joker2X.png");
        Path mbPath = Paths.get("src", "main","resources","images","JokerMB.png");

        jokerHG.setImage(new Image(hgPath.toUri().toString()));
        joker2X.setImage(new Image(twoxPath.toUri().toString()));
        jokerMB.setImage(new Image(mbPath.toUri().toString()));
        imgBttnReactAngry.setImage(reactionAngry);
        imgBttnReactClap.setImage(reactionClap);
        imgBttnReactCool.setImage(reactionCool);
        imgBttnReactSweaty.setImage(reactionSweaty);
        imgBttnReactLol.setImage(reactionLol);
    }

    /**
     * switches to the splash screen, for the leave button
     * @param event
     * @throws IOException
     */
    public void switchToSplash(ActionEvent event) throws IOException {
        mainCtrl.switchToSplash();
    }

    public void displayReactionLol() { displayReaction("Test", reactionLol); }

    public void displayReactionAngry() { displayReaction("Test", reactionAngry); }

    public void displayReactionClap() { displayReaction("Test", reactionClap); }

    public void displayReactionCool() { displayReaction("Test", reactionCool); }

    public void displayReactionSweaty() { displayReaction("Test", reactionSweaty); }

    public void displayReaction(String username, Image reaction) {
        List<AnchorPane> reactionList = new LinkedList<>(listViewReactions.getItems());
        GuiUtils.TimedReaction newReaction = GuiUtils.createNewReaction(username, reaction);

        reactionList.add(0, newReaction.getAnchorPane());
        if (reactionList.size() > 8) reactionList.remove(reactionList.size() - 1);
        listViewReactions.setItems(FXCollections.observableArrayList(reactionList));
        listViewReactions.setStyle("-fx-background-color: #000000");
        newReaction.start();
    }
}
