package client.scenes;

import client.utils.StatSharerSingleplayer;
import com.google.inject.Inject;
import client.Game;
import commons.game.Activity;
import commons.game.Question;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ResourceBundle;


public class SinglePlayer implements Initializable {

    /**
     * Used to share information between the game and the end-screen.
     * Here it is used to set the information.
     */
    private StatSharerSingleplayer statSharer;

    private MainCtrl mainCtrl;

    @FXML
    private ProgressBar timerBar;

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

    private double progress;

    private static final double EPSILON = 0.00001;
    Button[] answerbuttons = new Button[3];

    //game object to generate all questions and answers
    private Game game;

    //questionIterator to get the next question
    private Iterator<Question> questionIterator;

    //answersIterator to get the next correct answer
    private Iterator<Activity> answersIterator;

    //declare an animation timer
    private AnimationTimer tm = new TimerMethod();

    @Inject
    public SinglePlayer(StatSharerSingleplayer statSharer, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.statSharer = statSharer;
    }

    /**
     * starts a game, a timer bar and starts a first question
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        //When this screen starts, it will create a new game entity and fetch a question
        this.game = new Game();
        game.createQuestionList();

        //assigns the game questions, answers, and points list to the questionIterator
        this.questionIterator = Arrays.stream(game.questions).iterator();
        this.answersIterator = Arrays.stream(game.answers).iterator();

        //makes an array with references to the answer buttons
        answerbuttons[0]= answerA;
        answerbuttons[1] = answerB;
        answerbuttons[2] = answerC;

        displayQuestion(this.questionIterator.next());

        progress = 0;

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
        String correctanswer = answersIterator.next().toString();
        System.out.println("correct answer:"+ correctanswer);
        System.out.println("your answer:"+ useranswer.getText());

        //since we made an iterator of the answers the program checks if  the users button clicked is the right corresponding click
        //this function should definitely be tested

        //make the buttons there "correct colors" green for right answer red for the wrong answers
        for(Button answerbutton: answerbuttons){
            //the one corresponding with he next answers entry is the correct answer and  becomes green
            if(answerbutton.getText().equals(correctanswer)){
                answerbutton.setStyle("-fx-background-color: #309500;");
            }else{ //we make it red
                answerbutton.setStyle("-fx-background-color: #BD0000;");
            }
        }

        //after accordingly change the buttons colors
        //we retrieve the current style of all the buttons and add a border to the user chosen button
        for(Button answerbutton: answerbuttons){

            String currentstyle = answerbutton.getStyle();
            StringBuilder currentstylebuilder = new StringBuilder(currentstyle);
            //adding the border style
            currentstylebuilder.append("-fx-border-color: black; -fx-border-width: 3px;");
            String newstyle = currentstylebuilder.toString();

            if(answerbutton == useranswer){
                answerbutton.setStyle(newstyle);
            }
        }
        //after that we have to prompt of if the user was correct or not
        //user got the answer correct
        if(correctanswer.equals(useranswer.getText())){
            int currentpoints = Integer.parseInt(userpoint.getText());
            int newpoints = currentpoints + questionpoints;
            this.pointsInt = newpoints;
            userpoint.setText(String.valueOf(newpoints));
            prompt.setText("Correct");
        } else{
            prompt.setText("Incorrect");
        }

        //change scene state to the one where someone has answered the question
        //in which case the buttons should be disabled and change colors
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
            progress += 0.01;
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
                    displayQuestion(questionIterator.next());
                }else{
                    loadEndscreen();
                }
                //when timer ends and game hasn't ended we want to display the next question;
            }
        }
    }

    /**
     * displays the question and answers on the window and resets the game
     * @param question: a Question entity to display
     */
    public void displayQuestion(Question question){
        questionField.setText(question.toString());
        qNumber.setText(game.getCurRound() + " / 20");
        answerA.setText(question.getActivityList().get(0).toString());
        answerB.setText(question.getActivityList().get(1).toString());
        answerC.setText(question.getActivityList().get(2).toString());
        resetGamescreen();
    }

    /**
     * Switches to the end-screen after the game has finished.
     */
    private void loadEndscreen()  {
        tm.stop();
        this.statSharer.points = this.pointsInt;
        mainCtrl.switchToEndscreenSingleplayer();
    }
}
