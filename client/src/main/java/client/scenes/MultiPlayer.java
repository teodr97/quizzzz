package client.scenes;

import client.utils.GuiUtils;

import client.MySessionHandler;
import commons.models.Game;
import commons.models.Message;
import commons.models.MessageType;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import javax.inject.Inject;
import javax.swing.*;
import java.io.IOException;

import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import java.util.ResourceBundle;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;
import java.util.Timer;


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

    @FXML private Button timeJoker;

    @FXML public ProgressBar timerBar;

    @FXML public Text prompt;
    @FXML private Text userpoint;

    private int pointsInt = 0;

    @FXML private Text questionField;
    @FXML private Text qNumber;

    @FXML private ListView<AnchorPane> listViewReactions;

    private double progress;

    public double progressInc = 0.001;

    private static final double EPSILON = 0.00001;


    private WebSocketStompClient stompClient;

    private Message incomingmsg;

    public boolean gamended;

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

        this.mainCtrl.sessie.subscribe("/topic/questions", new StompFrameHandler() {

                    public Type getPayloadType(StompHeaders stompHeaders) {
                        return Message.class;
                    }

                    public void handleFrame(StompHeaders stompHeaders, Object payload) {
                        incomingmsg = (Message) payload;
                        if (incomingmsg.getMsgType() == MessageType.QUESTION) {
                            System.out.println("Gotten question");
                            questionField.setText(incomingmsg.getContent());
                        }
                        if(incomingmsg.getMsgType() == MessageType.GAME_ENDED){
                            gamended = true;
                            questionField.setText(incomingmsg.getContent());

                        }
                    }
                });

        this.mainCtrl.sessie.subscribe("/topic/jokers", new StompFrameHandler() {

            public Type getPayloadType(StompHeaders stompHeaders) {
                return Message.class;
            }

            public void handleFrame(StompHeaders stompHeaders, Object payload) {
                incomingmsg = (Message) payload;
                progressInc = 0.002;
            }
        });
        Message retrieveQ = new Message(MessageType.QUESTION, "client", "gib me question");
        this.mainCtrl.sessie.send("/app/getquestions", retrieveQ);

        Timer bartimer = new Timer();
        //CODE FOR MAKING THE TIMER BAR MOVE
        //we sync the server timer and the client timer with by just making sure that the timer bar is full after 10
        //seconds
        bartimer.scheduleAtFixedRate(new increaseTimerBar(this), 0, 10);
        if(gamended){
            timerBar.setProgress(0);

        }
    }

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






    /**
     * switches to the splash screen, for the leave button
     * @param event
     * @throws IOException
     */
    public void switchToSplash(ActionEvent event) throws IOException {
        mainCtrl.switchToSplash();
    }

    
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // THE USERNAME PASSED TO EACH OF THE BUTTON FUNCTIONS SHOULD BE
    // REPLACED WITH THE ACTUAL USERNAME OF THE LOCAl PLAYER! THE
    // USERNAME PARAMETER IS JUST A PLACEHOLDER.
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    /**
     * Used by the button for displaying a laugh reaction on the game screen.
     */
    public void displayReactionLol() { displayReaction("Test", reactionLol); }

    /**
     * Used by the button for displaying an angry reaction on the game screen.
     */
    public void displayReactionAngry() { displayReaction("Test", reactionAngry); }

    /**
     * Used by the button for displaying a clapping reaction on the game screen.
     */
    public void displayReactionClap() { displayReaction("Test", reactionClap); }

    /**
     * Used by the button for displaying a "cool" reaction on the game screen.
     */
    public void displayReactionCool() { displayReaction("Test", reactionCool); }

    /**
     * Used by the button for displaying a "sweaty" reaction on the game screen.
     */
    public void displayReactionSweaty() { displayReaction("Test", reactionSweaty); }

    /**
     * Displays a new reaction on the list to the right of the screen while also starting
     * the reaction's timer. Each reaction disappears either 3 seconds after its creation,
     * or if more new reactions have to be added to the list. This method should be used
     * for displaying reactions sent by other players in the game to every client.
     * @param username the username associated with the player sending the reaction
     * @param reaction the reaction sent by the player
     */
    public void displayReaction(String username, Image reaction) {
        // The reactions currently in the list.
        List<AnchorPane> reactionList = new LinkedList<>(listViewReactions.getItems());
        // Making a new reaction with given username and reaction image.
        GuiUtils.TimedReaction newReaction = GuiUtils.createNewReaction(username, reaction);

        reactionList.add(0, newReaction.getAnchorPane());
        // If there are more than 8 reactions after adding the newest one, remove the oldest one from the list.
        if (reactionList.size() > 8) reactionList.remove(reactionList.size() - 1);
        // Update the list.
        listViewReactions.setItems(FXCollections.observableArrayList(reactionList));
        //listViewReactions.setStyle("-fx-background-color: #000000");
        // Start the display timer for the newly added reaction.
        newReaction.start();
    }

    /**
     * Disables all answer buttons.
     */
    public void disableAnswers(){
        answerA.setDisable(true);
        answerB.setDisable(true);
        answerC.setDisable(true);

        return;
    }

    public void senddecreaseTimeForAll(){
        //disable the button if  clicked
        timeJoker.setDisable(true);
        mainCtrl.sessie.send("/app/clickedJoker", new Message(MessageType.TIME_JOKER, "client", "someone clicked the timer joker"));
    }


    //HANDLES the timebar
    private class TimerMethod extends AnimationTimer {
        //define the handle method
        @Override
        public void handle(long now) {
            //call the method
            handlee();
        }
        //method handlee
        private void handlee(){
            //making this smaller will slow down the times
            progress += 0.0025;
            //set the new progress
            timerBar.setProgress(progress);
            //checks if the progress is 1 and will display prompt accordingly
            // will also disable the buttons if the timer ends
            if((timerBar.getProgress() + EPSILON > 1 && timerBar.getProgress() - EPSILON <1)){
                //update the current round + 1
                game.setCurRound(game.getCurRound()+1);
                if(prompt != null){
                    if(prompt.getText().equals("")){
                        prompt.setText("Timer over");
                    }
                }
                //when timer ends and game hasn't ended we want to display the next question
                disableAnswers();
            }
            if((timerBar.getProgress() + EPSILON > 1.5 && timerBar.getProgress() - EPSILON <1.5)){
                //when timer ends and game hasn't ended we want to display the next question;

                //when timer ends and game hasn't ended we want to display the next question;
            }
        }
    }
}





