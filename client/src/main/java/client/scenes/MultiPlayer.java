package client.scenes;

import client.utils.GuiUtils;


import commons.models.*;

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

import java.io.IOException;

import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import java.util.ResourceBundle;



import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;



import org.springframework.web.socket.messaging.WebSocketStompClient;


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


    public Timer bartimer;

    public double progress;

    public double progressInc = 0.001;

    private static final double EPSILON = 0.00001;


    private WebSocketStompClient stompClient;

    private Message incomingmsg;

    private Question incomingq;

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
        System.out.println(timerBar.getStyle());

        this.mainCtrl.sessie.subscribe("/topic/questions", new StompFrameHandler() {

                    public Type getPayloadType(StompHeaders stompHeaders) {
                        return Question.class;
                    }

                    public void handleFrame(StompHeaders stompHeaders, Object payload) {
                        incomingq= (Question) payload;

                        System.out.println(incomingq.toString());
                        questionField.setText(incomingq.getQuestion());
                        startBar();



                    }
                });

//        this.mainCtrl.sessie.subscribe("/topic/questions", new StompFrameHandler() {
//
//            public Type getPayloadType(StompHeaders stompHeaders) {
//                return Message.class;
//            }
//
//            public void handleFrame(StompHeaders stompHeaders, Object payload) {
//                incomingmsg = (Message) payload;
//                if (incomingmsg.getMsgType() == MessageType.QUESTION) {
//                    System.out.println("Gotten question");
//                    questionField.setText(incomingmsg.getContent());
//                    startBar();
//
//                }
//                if(incomingmsg.getMsgType() == MessageType.GAME_ENDED){
//                    //variable will probably be replaced by a game class atrribute
//                    gamended = false;
//                    timerBar.setProgress(0);
//                    questionField.setText(incomingmsg.getContent());
//
//
//                }
//
//            }
//        });

//

        this.mainCtrl.sessie.subscribe("/topic/jokers", new StompFrameHandler() {

            public Type getPayloadType(StompHeaders stompHeaders) {
                return Message.class;
            }

            public void handleFrame(StompHeaders stompHeaders, Object payload) {
                incomingmsg = (Message) payload;
                System.out.println("someone clicked a joker");
                progressInc = 0.002;

                timerBar.setStyle("-fx-accent: red");


            }
        });

//        Message retrieveQ = new Message(MessageType.QUESTION, "client", "gib me question");
//        this.mainCtrl.sessie.send("/app/getquestions", retrieveQ);

//        bartimer = new Timer();
////        CODE FOR MAKING THE TIMER BAR MOVE
////        we sync the server timer and the client timer with by just making sure that the timer bar is full after 10
////        seconds
//        bartimer.scheduleAtFixedRate(new increaseTimerBar(this), 0, 10);
    }

    /**
     * switches to the splash screen, for the leave button
     * @param event
     * @throws IOException
     */
    public void switchToSplash(ActionEvent event) throws IOException {
        mainCtrl.switchToSplash();
    }

    /**
     * Starts the timer bar sheduled task for displaying the progressbar
     */
    public void startBar(){
        timerBar.setStyle("-fx-accent: blue");
        progressInc = 0.001;
        progress = 0;
        timerBar.setProgress(progress);
        bartimer = new Timer();
        bartimer.scheduleAtFixedRate(new IncreaseTimerBar(this), 0, 10);
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

    /**
     * Send that the timer joker has been used to all clients subscriped to the /topic/joker endpoint
     */
    public void senddecreaseTimeForAll(){
        //disable the button if  clicked
        timeJoker.setDisable(true);
        mainCtrl.sessie.send("/topic/jokers", new Message(MessageType.TIME_JOKER, "client", "someone clicked the timer joker"));
    }




}





