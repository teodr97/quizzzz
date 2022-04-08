package client.scenes;

import client.utils.QuestionRetriever;
import client.utils.SingleplayerHighscoreHandler;
import client.utils.StatSharerSingleplayer;
import com.google.inject.Inject;
import commons.game.Activity;
import commons.models.FileInfo;
import commons.models.Question;
//import commons.game.Question;
import commons.models.Game;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.apache.tomcat.util.codec.binary.Base64;
import org.glassfish.jersey.client.ClientConfig;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;


public class SinglePlayer implements Initializable {

    /**
     * Used to share information between the game and the end-screen.
     * Here it is used to set the information.
     */
    private StatSharerSingleplayer statSharer;

    private SingleplayerUsername singleplayerUsername;

    private MainCtrl mainCtrl;
    private ServerSelectorCtrl serverSelectorCtrl;

    @FXML
    private ProgressBar timerBar;

    private final Image optionPlaceholderImage =
            new Image(Paths.get("src", "main","resources","images","Test!.png").toUri().toString());

    @FXML private Button answerA;
    @FXML private Button answerB;
    @FXML private Button answerC;

    @FXML private ImageView imgBttnA;
    @FXML private ImageView imgBttnB;
    @FXML private ImageView imgBttnC;

    @FXML
    private Text prompt;

    @FXML
    private Text userpoint;
    private int pointsInt = 0;

    @FXML
    private Text questionField;

    @FXML
    private Text qNumber;

    private double progress;

    private static final double EPSILON = 0.00001;
    List<Button> answerButtons = new ArrayList<>(3);

    //game object to generate all questions and answers
    private Game game;

    //player's username
    private String username;

    //questionIterator to get the next question
    private Iterator<Question> questionIterator;

    // The question being currently displayed
    private Question currentQuestion;

    //declare an animation timer
    private AnimationTimer tm = new TimerMethod();

    @Inject
    public SinglePlayer(StatSharerSingleplayer statSharer,
                        SingleplayerUsername singleplayerUsername,
                        MainCtrl mainCtrl,
                        ServerSelectorCtrl serverSelectorCtrl) {
        this.mainCtrl = mainCtrl;
        this.singleplayerUsername = singleplayerUsername;
        this.statSharer = statSharer;
        this.serverSelectorCtrl = serverSelectorCtrl;

    }

    public SinglePlayer(){

    }

    /**
     * Setting up the username.
     * @param username
     */
    public void setUsername(String username){
        this.username = username;
        System.out.println(this.username);
    }

    /**
     * starts a game, a timer bar and starts a first question
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        //When this screen starts, it will create a new game entity and fetch a question
        this.game = new Game();
        this.statSharer.reset();

        ArrayList<Activity> alist = new ArrayList<>();
        alist = (ArrayList<Activity>) new QuestionRetriever(serverSelectorCtrl).retrieveAllActivitySetFromServer();


        //alist = (ArrayList<Activity>) new QuestionRetriever(mainCtrl).retrieveActivitySetFromServer();

        game.createQuestionList(alist);

        //game.createQuestionList(alist);
        this.statSharer.totalAnswers = game.getTotalRounds();

        //assigns the game questions, answers, and points list to the questionIterator
        this.questionIterator = Arrays.stream(game.questions).iterator();



        //makes an array with references to the answer buttons
        answerButtons.add(0, answerA);
        answerButtons.add(1, answerB);
        answerButtons.add(2, answerC);

        imgBttnA.setImage(optionPlaceholderImage);
        imgBttnB.setImage(optionPlaceholderImage);
        imgBttnC.setImage(optionPlaceholderImage);

        currentQuestion = this.questionIterator.next();
        displayQuestion(currentQuestion);

        progress = 0;

        this.setUsername(singleplayerUsername.getUsername());

        //start the timer
        tm.start();
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
        int questionpoints = (int)(500 - 250*progress);
        String correctanswer = currentQuestion.getActivityList().get(currentQuestion.getCorrectAnswerIndex()).getTitle();
        System.out.println("correct answer: "+ correctanswer);
        System.out.println("your answer: "+ useranswer.getText());

        //since we made an iterator of the answers the program checks if  the users button clicked is the right corresponding click
        //this function should definitely be tested

        // make the buttons there "correct colors" green for right answer red for the wrong answers
        for(Button answerbutton: answerButtons){
            // the one corresponding with he next answers entry is the correct answer and  becomes green
            if(Question.hasCorrectAnswer(currentQuestion, answerButtons.indexOf(answerbutton))){
                setButtonsStyle(currentQuestion.getCorrectAnswerIndex());
            }else{ //we make it red
                setButtonsStyle(currentQuestion.getCorrectAnswerIndex());
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
        if(Question.hasCorrectAnswer(currentQuestion, answerButtons.indexOf(useranswer))){
            int currentpoints = Integer.parseInt(userpoint.getText());
            int newpoints = currentpoints + questionpoints;
            this.pointsInt = newpoints;
            userpoint.setText(String.valueOf(newpoints));
            prompt.setText("Correct");
            this.statSharer.correctAnswers++;
        } else{
            prompt.setText("Incorrect");
        }

        // change scene state to the one where someone has answered the question
        // in which case the buttons should be disabled and change colors
        disableAnswers();

        return;
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
     * Enables all answer buttons.
     */
    public void enableAnswers(){
        answerA.setDisable(false);
        answerB.setDisable(false);
        answerC.setDisable(false);

        return;
    }

    /**
     * Sets the font size of the button texts to a variable size, depending on the length
     * of the text displayed on each button. The button also has its color switch to the
     * one indicated by the argument.
     * @param color The color the button should have
     */
    private void setButtonsStyle(String color) {
        for (Button bttn : answerButtons) {
            bttn.setStyle(String.format("-fx-background-color: %s; -fx-font-size: %d;", color,
                    (int)(-Math.pow((bttn.getText().length() - 20), -3) * 0.5 + 2) * 10));
        }
    }

    /**
     * Set the image for the activity displayed as an option to a single-player question.
     * @param activity The activity from which the image will be retrieved
     * @param imageView The corresponding slot in which the image goes
     */
    private void displayActivityImage(Activity activity, ImageView imageView) {
        Activity a = activity;
        String imagepath = a.getImage_path();

        //request its image path
        FileInfo fileInfo = ClientBuilder.newClient(new ClientConfig())
                .target(serverSelectorCtrl.getServer()).path("/images/get")
                .queryParam("image_path", imagepath )
                .request(APPLICATION_JSON).accept(APPLICATION_JSON)
                .get(new GenericType<>() {});

        //decode encoded string
        String s = fileInfo.getEncoding();
        InputStream in = new ByteArrayInputStream(Base64.decodeBase64(s));

        //set the test image
        imageView.setImage(new Image(in));
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
     * Resets the game screen for the next round.
     */
    public void resetGamescreen(){
        //resetting the answer buttons
        //color and clickability, the timer bar and the text prompt

        int prefWidth;
        int prefHeight;

        // Buttons.
        for (Button bttn : answerButtons) {
            if (bttn.getText().contains(" Wh")) {
                prefWidth = 300;
                prefHeight = 100;
                imgBttnA.setVisible(false);
                imgBttnB.setVisible(false);
                imgBttnC.setVisible(false);
                //bttn.setContentDisplay(ContentDisplay.CENTER);
                //bttn.setTranslateY(75);
            }
            else {
                prefWidth = 300;
                prefHeight = 100;
                imgBttnA.setVisible(true);
                imgBttnB.setVisible(true);
                imgBttnC.setVisible(true);
                //bttn.setContentDisplay(ContentDisplay.TOP);
                //bttn.setTranslateY(25);
            }
            bttn.setMinSize(prefWidth, prefHeight);
            bttn.setPrefSize(prefWidth, prefHeight);
            bttn.setMaxSize(prefWidth, prefHeight);
        }
        setButtonsStyle("#0249bd");
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
     * Switches the scene to Splash.
     * @param event
     * @throws IOException
     */
    public void switchToSplash(ActionEvent event) throws IOException{
        tm.stop();
        mainCtrl.switchToSplash();
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
                if(questionIterator.hasNext()){
                    currentQuestion = questionIterator.next();
                    displayQuestion(currentQuestion);
                }else{
                    loadEndscreen();
                }
                //when timer ends and game hasn't ended we want to display the next question;
            }
        }
    }

    /**
     * Displays the question and answers on the window and resets the game.
     * @param question: a Question entity to display
     */
    public void displayQuestion(Question question){
        questionField.setText(question.getQuestion());
        qNumber.setText(game.getCurRound() + " / 20");
        answerA.setText(question.getActivityList().get(0).getTitle());
        answerB.setText(question.getActivityList().get(1).getTitle());
        answerC.setText(question.getActivityList().get(2).getTitle());
        displayActivityImage(question.getActivityList().get(0), imgBttnA);
        displayActivityImage(question.getActivityList().get(1), imgBttnB);
        displayActivityImage(question.getActivityList().get(2), imgBttnC);

        resetGamescreen();
    }

    /**
     * Switches to the end-screen after the game has finished.
     */
    private void loadEndscreen()  {
        tm.stop();
        this.statSharer.points = this.pointsInt;
        mainCtrl.switchToEndscreenSingleplayer();
        logGame();
    }

    /**
     * Logs the current game and writes it to a local file.
     */
    private void logGame() {
        SingleplayerHighscoreHandler shh = null;
        try {
            shh = SingleplayerHighscoreHandler.getHighscores();
            shh.saveNewEntry(this.username, this.statSharer.points);
        } catch (FileNotFoundException e) {
            System.out.println("Game could not be recorded.");
            e.printStackTrace();
        }
    }


}
