package client.scenes;

import client.utils.GuiUtils;
import commons.models.*;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import javafx.application.Platform;
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
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;


public class MultiPlayer implements Initializable {

    private Game game;
    private MainCtrl mainCtrl;
    private static final String SERVER = "http://localhost:8080/";


    private Image reactionAngry;
    private Image reactionLol;
    private Image reactionClap;
    private Image reactionCool;
    private Image reactionSweaty;

    @FXML private ImageView jokerHG;
    @FXML private ImageView joker2X;
    @FXML private ImageView jokerMB;
    @FXML private ImageView imgBttnReactLol;
    @FXML private ImageView imgBttnReactAngry;
    @FXML private ImageView imgBttnReactCool;
    @FXML private ImageView imgBttnReactClap;
    @FXML private ImageView imgBttnReactSweaty;

    @FXML public Button answerA;
    @FXML public Button answerB;
    @FXML public Button answerC;

    private ArrayList<Button> answerButtons;

    //joker buttons
    @FXML private Button timeJoker;
    @FXML private Button removeJoker;
    @FXML private Button doubleJoker;

    private int scoreMultiplier = 1;



    @FXML public ProgressBar timerBar;

    @FXML public Text prompt;
    @FXML private Text userpoint;

    private int pointsInt = 0;

    @FXML private Text questionField;
    @FXML private Text qNumber;

    @FXML private ListView<AnchorPane> listViewReactions;


    public Timer bartimer = new Timer();

    public double progress;

    public double progressInc = 0.001;

    private static final double EPSILON = 0.00001;

    int displaycounter= 0;


    private WebSocketStompClient stompClient;

    private Message incomingmsg;
    private Emote incomingEmote;
    private Question incomingq;


    public boolean gamended;
    private ServerSelectorCtrl serverSelectorCtrl;

    private String chatstyle;

    /**
     * Constructor function.
     * @param mainCtrl The UI controller for the screen
     */
    @Inject
    public MultiPlayer(MainCtrl mainCtrl, ServerSelectorCtrl serverSelectorCtrl) {
        this.mainCtrl = mainCtrl;
        this.serverSelectorCtrl = serverSelectorCtrl;
    }

    /**
     * Draws the button images for the jokers and the emotes, getting the path of
     * those within the project. Caution: if the images are moved to another folder,
     * the paths used in this method must be changed accordingly.
     */
    private void drawButtonImages() {
    }

    /**
     * Initialises the multiplayer game screen, rendering the UI elements and
     * subscribing to the game events on the server.
     * @param location The location of the game screen
     * @param resources The resource bundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        reactionAngry = new Image(Paths.get("src", "main","resources","images","reactAngry.png").toUri().toString());
        reactionLol = new Image(Paths.get("src", "main","resources","images","reactLol.png").toUri().toString());
        reactionClap = new Image(Paths.get("src", "main","resources","images","reactClap.png").toUri().toString());
        reactionCool = new Image(Paths.get("src", "main","resources","images","reactCool.png").toUri().toString());
        reactionSweaty = new Image(Paths.get("src", "main","resources","images","reactSweaty.png").toUri().toString());
        Path hgPath = Paths.get("src", "main","resources","images","JokerHG.png");
        Path twoxPath = Paths.get("src", "main","resources","images","Joker2X.png");
        Path mbPath = Paths.get("src", "main","resources","images","JokerMB.png");

        imgBttnReactAngry.setImage(reactionAngry);
        imgBttnReactClap.setImage(reactionClap);
        imgBttnReactCool.setImage(reactionCool);
        imgBttnReactSweaty.setImage(reactionSweaty);
        imgBttnReactLol.setImage(reactionLol);
        jokerHG.setImage(new Image(hgPath.toUri().toString()));
        joker2X.setImage(new Image(twoxPath.toUri().toString()));
        jokerMB.setImage(new Image(mbPath.toUri().toString()));
        answerButtons = new ArrayList<>();
        answerButtons.add(answerA);
        answerButtons.add(answerB);
        answerButtons.add(answerC);

        chatstyle = listViewReactions.getStyle();

        settingUp();
    }

    /**
     * Setting up the game properties.
     */
    public void settingUp(){
        System.out.println(timerBar.getStyle());

        // Subscribes to the path through which the questions are provided.
        this.mainCtrl.sessie.subscribe("/topic/questions", new StompFrameHandler() {
            // The type of the object sent by the server.
            public Type getPayloadType(StompHeaders stompHeaders) {
                return Question.class;
            }

            // The method called when the client receives a packet from the server.
            public void handleFrame(StompHeaders stompHeaders, Object payload) {
                // if we get new question we reset the score multiplier to 1
                //since someone could have clicked a double points joker in the previous round
                try{
                    System.out.println("Client received question from server");
                    scoreMultiplier = 1;
                    bartimer.cancel();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
                incomingq= (Question) payload;

                //questionField.setText(incomingq.getQuestion());
                // Wrapper to run the UI elements on the JFX thread.
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        displayQuestion(incomingq);
                    }
                });
                startBar();
            }
        });

        /*
         * The client-server communication handler for the emotes. Subscribes to the path
         * through which the server sends the player reactions.
         */
        this.mainCtrl.sessie.subscribe("/topic/emotes", new StompFrameHandler() {
            // Get the object type sent through the path.
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Emote.class;
            }

            // The method which is called when the client receives a packet from the server.
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                // The reaction sent by a player over the server.
                Image reactionImage;

                // The packet sent through the server. Contains a String representing
                // the username of the player, and a MessageType that holds the type
                // of reaction (reaction id).
                incomingEmote = (Emote) payload;

                if(displaycounter == 1 ){
                    displaycounter = 0;
                    return;
                }
                if(incomingEmote.getUsername().equals(mainCtrl.getPlayer().getNickname())){
                    displaycounter++;
                }

                switch (incomingEmote.getReactionId()) {
                    case REACT_LOL: reactionImage = reactionLol; break;
                    case REACT_ANGRY: reactionImage = reactionAngry; break;
                    case REACT_SWEATY: reactionImage = reactionSweaty; break;
                    case REACT_COOL: reactionImage = reactionCool; break;
                    case REACT_CLAP: reactionImage = reactionClap; break;
                    default: throw new IllegalArgumentException("The reaction id sent by the server to the client is invalid!");
                }


                displayReaction(incomingEmote.getUsername(), reactionImage);


            }
        });



        /*
         * The client-server communication handler for the jokers. Subscribes to the path
         * through which the server sends the player reactions.
         */
        this.mainCtrl.sessie.subscribe("/topic/jokers", new StompFrameHandler() {
            // Get the object type sent through the path.
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return Message.class;
            }

            // The method which is called when the client receives a packet from the server.
            @Override
            public void handleFrame(StompHeaders stompHeaders, Object payload) {
                incomingmsg = (Message) payload;

                if(incomingmsg.getMsgType() == MessageType.TIME_JOKER){
                    prompt.setText("Timer joker used");
                    progressInc = progressInc*2;

                    timerBar.setStyle("-fx-accent: red");
                }
                if(incomingmsg.getMsgType() == MessageType.REMOVE_JOKER){
                    //maybe we want functionality here to notify the other
                    //client that someone used a joker
                    System.out.println("someone used a joker");
                }

                if(incomingmsg.getMsgType() == MessageType.DOUBLE_JOKER){
                    //handleDoubleJoker();
                    System.out.println("someone used a joker");
                }
            }
        });
    }


    /**
     * Send that the double points joker has been used to all clients subscriped to the /topic/joker endpoint
     */
    //this joker can be handled fully on client side since it doens't effect the other players
    public void handleDoubleJoker(){
        prompt.setText("Double joker used");
        //disable the button if  clicked
        scoreMultiplier = 2;
        doubleJoker.setDisable(true);

        //we could send a message to the server for stat tracking
        //mainCtrl.sessie.send("/app/clickedJoker", new Message(MessageType.DOUBLE_JOKER, "client", "someone clicked the double points joker"));
    }


    /**
     * Send that the removal joker has been used to all clients subscriped to the /topic/joker endpoint
     */
    //this joker can be handled fully on client since it doens't effect the other players
    public void handleRemovalJoker(){
        //disable the button if  clicked
        removeJoker.setDisable(true);
        ArrayList<Button> wrongbuttons = new ArrayList<Button>();
        for(Button button : answerButtons){
            if(answerButtons.indexOf(button) != incomingq.getCorrectAnswerIndex()){
                wrongbuttons.add(button);
            }
        }
        int randomindex= new Random().nextInt(2);
        wrongbuttons.get(randomindex).setDisable(true);

    }


    /**
     * Displays the question and answers on the window and resets the game.
     * @param question: a Question entity to display
     */
    public void displayQuestion(Question question){
        questionField.setText(question.getQuestion());
        qNumber.setText(question.getQuestionNo() + " / 20");
        answerA.setText(question.getActivityList().get(0).getTitle());
        answerB.setText(question.getActivityList().get(1).getTitle());
        answerC.setText(question.getActivityList().get(2).getTitle());
        resetGamescreen();
    }

    /**
     * Resets the game screen for the next round.
     */
    public void resetGamescreen(){
        //resetting the answer buttons
        //color and clickability, the timer bar and the text prompt

        //buttons
        answerA.setStyle("-fx-background-color: #0249bd;");
        answerB.setStyle("-fx-background-color: #0249bd;");
        answerC.setStyle("-fx-background-color: #0249bd;");
        enableAnswers();

        //timerbar
        //we don't have to set the progress of the timer bar
        //because the animation timer continually does that
        progress = 0;

        //prompt
        prompt.setText("");

        return;
    }

    /**
     * Enables all answer buttons.
     */
    public void enableAnswers(){
        answerA.setDisable(false);
        answerB.setDisable(false);
        answerC.setDisable(false);

        return;
    }

    /**
     * Checks if the answer in singleplayer is correct
     * @param event
     * @throws IOException
     * @throws InterruptedException
     */
    public void checkAnswer(ActionEvent event) throws IOException, InterruptedException {
        //check answer will also have to call a function:
        //disableAnswers so the uses can't click the answers after already choosing one

        //get the button clicked from the event parameter
        Button useranswer = (Button) event.getTarget();

        //gets the amount of points to be handed, and assigns the correct answer to a variable
        int questionpoints = (int)((500 - 250*progress)*scoreMultiplier);
        String correctanswer = incomingq.getActivityList().get(incomingq.getCorrectAnswerIndex()).getTitle();
        System.out.println("correct answer: "+ correctanswer);
        System.out.println("your answer: "+ useranswer.getText());

        //since we made an iterator of the answers the program checks if  the users button clicked is the right corresponding click
        //this function should definitely be tested

        // make the buttons there "correct colors" green for right answer red for the wrong answers
        for(Button answerbutton: answerButtons){
            // the one corresponding with he next answers entry is the correct answer and  becomes green
            if(Question.hasCorrectAnswer(incomingq, answerButtons.indexOf(answerbutton))){
                setButtonsStyle(incomingq.getCorrectAnswerIndex());
            }else{ //we make it red
                setButtonsStyle(incomingq.getCorrectAnswerIndex());
            }
        }

        //after accordingly change the buttons colors
        //we retrieve the current style of all the buttons and add a border to the user chosen button
        for(Button answerbutton: answerButtons){

            String currentstyle = answerbutton.getStyle();
            StringBuilder currentstylebuilder = new StringBuilder(currentstyle);
            //adding the border style
            currentstylebuilder.append("-fx-border-color: black; -fx-border-width: 3px;");
            String newstyle = currentstylebuilder.toString();

            if(answerbutton == useranswer){
                answerbutton.setStyle(newstyle);
            }
        }
        // after that we have to prompt of if the user was correct or not
        // user got the answer correct
        if(Question.hasCorrectAnswer(incomingq, answerButtons.indexOf(useranswer))){
            int currentpoints = Integer.parseInt(userpoint.getText());
            int newpoints = currentpoints + questionpoints;
            this.pointsInt = newpoints;
            int points = ClientBuilder.newClient(new ClientConfig())
                    .target(serverSelectorCtrl.getServer()).path("/game/addPoint")
                    .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE)
                    .queryParam( "player",  mainCtrl.getPlayer().getNickname())
                    .queryParam("point", questionpoints)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get(new GenericType<Integer>() {});
            System.out.println("Incremented points" + points);
            userpoint.setText(String.valueOf(newpoints));
            prompt.setText("Correct");
            //this.statSharer.correctAnswers++;

        } else{
            prompt.setText("Incorrect");
        }

        // change scene state to the one where someone has answered the question
        // in which case the buttons should be disabled and change colors
        disableAnswers();

        return;

    }

    /**
     * Sets the font size of the button texts to a variable size, depending on the length
     * of the text displayed on each button. The button also has its color switch to either
     * green or red depending on whether the answer is correct or not.
     * @param correctAnswerIndex The index of the correct answer button
     */
    private void setButtonsStyle(int correctAnswerIndex) {
        String color = "#000000";

        for (Button bttn : answerButtons) {
            if (answerButtons.indexOf(bttn) == correctAnswerIndex) color = "#309500";
            else color = "#BD0000";
            bttn.setStyle(String.format("-fx-background-color: %s; -fx-font-size: %d;", color,
                    (int)(-Math.pow((bttn.getText().length() - 20), -3) * 0.5 + 2) * 10));
        }
    }

    /**
     * If the event is executed then the scene switches to Splash.fxml
     * @param event
     * @throws IOException
     */
    public void leaveGame(ActionEvent event) throws IOException {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/game/leave")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(mainCtrl.getPlayer(), APPLICATION_JSON));

        this.mainCtrl.switchToSplash();
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


    /**
     * Used by the button for displaying a laugh reaction on the game screen.
     */
    public void displayReactionLol() {
        mainCtrl.sessie.send("/topic/emotes", new Emote(mainCtrl.getPlayer().getNickname(), MessageType.REACT_LOL));
    }

    /**
     * Used by the button for displaying an angry reaction on the game screen.
     */
    public void displayReactionAngry() {
        mainCtrl.sessie.send("/topic/emotes", new Emote(mainCtrl.getPlayer().getNickname(), MessageType.REACT_ANGRY));
    }

    /**
     * Used by the button for displaying a clapping reaction on the game screen.
     */
    public void displayReactionClap() {
        mainCtrl.sessie.send("/topic/emotes", new Emote(mainCtrl.getPlayer().getNickname(), MessageType.REACT_CLAP));
    }

    /**
     * Used by the button for displaying a "cool" reaction on the game screen.
     */
    public void displayReactionCool() {
        mainCtrl.sessie.send("/topic/emotes", new Emote(mainCtrl.getPlayer().getNickname(), MessageType.REACT_COOL));
    }

    /**
     * Used by the button for displaying a "sweaty" reaction on the game screen.
     */
    public void displayReactionSweaty() {
        mainCtrl.sessie.send("/app/sendEmote", new Emote(mainCtrl.getPlayer().getNickname(), MessageType.REACT_SWEATY));
    }

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
        //
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                listViewReactions.setItems(FXCollections.observableArrayList(reactionList));
                listViewReactions.setStyle(chatstyle);
            }
        });
        //listViewReactions.setItems(FXCollections.observableArrayList(reactionList));
        //listViewReactions.setStyle("-fx-background-color:  linear-gradient(#02274f, #101b25)");
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

        //we need to send that someone clicked this joker to every player
        mainCtrl.sessie.send("/topic/jokers", new Message(MessageType.TIME_JOKER, "client", "someone clicked the timer joker"));
    }
}





