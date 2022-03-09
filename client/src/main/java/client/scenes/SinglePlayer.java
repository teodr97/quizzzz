package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import commons.game.Activity;
import commons.game.Game;
import commons.game.Question;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.Timer;

import static com.google.inject.Guice.createInjector;


public class SinglePlayer implements Initializable {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Stage primaryStage;


    private Stage stage;
    private Scene scene;

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

    @FXML
    private Text questionField;

    private double progress;

    double EPSILON = 0.00001;
    Button[] answerbuttons = new Button[3];

    private Game game;

    //in the fields below we hardcode the answers and questions array
    //I think in the future it would be handy if we had a question objects which had the actual question as a string
    // and then also it's corresponding answer. This answer should be exactly equal to the text on the button so that
    // we can check for equality between what the user pressed and what the answer is.

    //hardcoded questions array this information will need to be retrieved from the database
    //I think this is done with the server field
    private Iterator<Question> questionIterator;

    ////hardcoded answers array this information will need to be retrieved from the database
    private Iterator<Activity> answersIterator;

    //hardcoded points array for each question so 4 entries array
    private int[] points = new int[9];
    private Iterator<Integer> pointsIterator;

    // amount of question asked;
    private int qnumber;

    //declare an animation timer
    private AnimationTimer tm = new TimerMethod();


    @Inject
    public SinglePlayer(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * starts a game, a timer bar and starts a first question
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        //When this screen starts, it will create a new game entity and fetch a question
        this.game = new Game();
        game.createQuestionList();

        this.questionIterator = Arrays.stream(game.questions).iterator();
        this.answersIterator = Arrays.stream(game.answers).iterator();
        this.pointsIterator = Arrays.stream(points).iterator();


        //Again with he hardcoding
        // this time for each question we assign an amount of points

        // for now the workflow I (Jordano) think the workflow of the project will look like this:
        // we retrieve 20 activities from the database these activities get transformed into question objects.
        // in this transformation to question objects we also assign points to the question.
        //so like the answers the points will be attributes of the questions object
        // but for now hardcode
        points[0] =  100;
        points[1] = 150;
        points[2] = 200;
        points[3] = 250;
        points[4] =  100;
        points[5] = 150;
        points[6] = 200;
        points[7] = 250;
        points[8] = 250;

        //makes an array with references to the answer buttons
        answerbuttons[0]= answerA;
        answerbuttons[1] = answerB;
        answerbuttons[2] = answerC;

        displayQuestion(this.questionIterator.next());

        qnumber = 0;
        progress = 0;

        //start the timer
        tm.start();
    }

    //Sets and shows the scene.
    public void setAndShowScenes(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //check answers in singleplayer
    // check answer also make sures the layers points get updated
    public void checkAnswer(ActionEvent event) throws IOException, InterruptedException {
        //check answer will also have to call a function:
        //disableAnswers so the uses can't click the answers after already choosing one

        //get the button clicked from the event parameter
        Button useranswer = (Button) event.getTarget();

        int questionpoints = pointsIterator.next();
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
            userpoint.setText(String.valueOf(newpoints));
            prompt.setText("Correct");
        } else{
            prompt.setText("Incorrect");
        }

        //change scene state to the one where someone has answered the question
        //in which case the buttons should be disabled and change colors
        Disableanswers();

        return;
    }

    //Disables all the answer buttons
    public void Disableanswers(){
        answerA.setDisable(true);
        answerB.setDisable(true);
        answerC.setDisable(true);

        return;
    }
    //Enables all the answer buttons
    public void Enableanswers(){
        answerA.setDisable(false);
        answerB.setDisable(false);
        answerC.setDisable(false);

        return;
    }
    //Enables all the answer buttons
    public void resetGamescreen(){
        //resetting the answer buttons
        //color and clickability, the timer bar and the text prompt

        //buttons
        answerA.setStyle("-fx-background-color: #0249bd;");
        answerB.setStyle("-fx-background-color: #0249bd;");
        answerC.setStyle("-fx-background-color: #0249bd;");
        Enableanswers();

        //timerbar
        //we don't have to set the progress of the timer bar
        //because the animation timer continually does that
        progress = 0;

        //prompt
        prompt.setText("");

        return;
    }

    //goes to the next question screen
    public void nextQuestion(){
        questionField.setText(this.questionIterator.next().toString());
        resetGamescreen();

        return;
    }

    //If the event is executed then the scene switches to Splash.fxml
    public void switchToSplash(ActionEvent event) throws IOException{
        tm.stop();
        var overview = FXML.load(Splash.class, "client", "scenes", "Splash.fxml");
        scene = new Scene(overview.getValue());
        setAndShowScenes(event);
    }

    //HANDLES   the timebar
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
                qnumber += 1;
                if(prompt != null){
                    if(prompt.getText().equals("")){
                        prompt.setText("Timer over");
                    }
                }
                //when timer ends and game hasn't ended we want to display the next question
                Disableanswers();
            }
            if((timerBar.getProgress() + EPSILON > 1.5 && timerBar.getProgress() - EPSILON <1.5)){
                //when timer ends and game hasn't ended we want to display the next question;
                if(questionIterator.hasNext()){
                    displayQuestion(questionIterator.next());
                }else{
                    tm.stop();
                }
                //when timer ends and game hasn't ended we want to display the next question;
            }
        }
    }

    /**
     * displays the question and answers on the window
     * @param question: a Question entity to display
     */
    public void displayQuestion(Question question){
        questionField.setText(question.toString());
        answerA.setText(question.getActivities()[0].toString());
        answerB.setText(question.getActivities()[1].toString());
        answerC.setText(question.getActivities()[2].toString());
        resetGamescreen();
    }
}
