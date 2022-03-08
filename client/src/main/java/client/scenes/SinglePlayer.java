package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import commons.game.Game;
import commons.game.Question;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

    private double progress;

    @FXML
    private Text questionField;

    double EPSILON = 0.00001;

    private Game game;



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
        nextQuestion();

        //starts the timer
        Timer timerobj = new Timer();
        progress = 0;

        //declare an animation timer
        AnimationTimer tm = new TimerMethod();
        //start the timer
        tm.start();




    }

    //Sets and shows the scene.
    public void setAndShowScenes(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //check answers in singleplayer this needs can be more profesional
    //by putting it in the singleplayer class
    public void checkAnswer(ActionEvent event) throws IOException {
        //check answer will also have to call a function:
        //disableAnswers so the uses can't click the answers after already choosing one
        Button useranswer = (Button) event.getTarget();

        if(useranswer == answerA){
            prompt.setText("Correct");
            answerA.setStyle("-fx-background-color: #309500; -fx-border-color: black; -fx-border-width: 3px;");

        }else{
            prompt.setText("Incorrect");
        }

        if(useranswer == answerB){
            answerB.setStyle("-fx-background-color: #BD0000;-fx-border-color: black; -fx-border-width: 3px;");

        }else if(useranswer == answerC){
            answerC.setStyle("-fx-background-color: #BD0000; -fx-border-color: black; -fx-border-width: 3px;");
        }

        //change scene sate to the one where someone has answererd the question
        //in which case the buttons hould bedisabled and change collors
        answerA.setDisable(true);
        answerB.setDisable(true);
        answerB.setStyle("-fx-background-color: #BD0000;");
        answerC.setDisable(true);
        answerC.setStyle("-fx-background-color: #BD0000;");




        System.out.println("user choose answer");

        return;
    }


    //Diables all the answer buttons
    public void Disableanswers(){


        answerA.setDisable(true);

        answerB.setDisable(true);

        answerC.setDisable(true);

        return;
    }

    //If the event is executed then the scene switches to Splash.fxml
    public void switchToSplash(ActionEvent event) throws IOException{
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
            progress += 0.0001;
            //set the new progress
            timerBar.setProgress(progress);
            //checks if the progress is 1 and will display prompt accordingly
            // will also diable the buttons if the timer ends
            if((timerBar.getProgress() + EPSILON > 1 && timerBar.getProgress() - EPSILON <1)){
                prompt.setText("Timer over");
                Disableanswers();
            }
        }
    }

    /**
     * generates a new question to display, and resets answers
     */
    public void nextQuestion(){
        //increments the current round
        this.game.setCurRound(this.game.getCurRound() + 1);

        Question q = this.game.createQuestion();

        //takes each activity's title and puts it in a String[]
        String[] ans = new String[3];
        for(int i = 0; i < 3; i++){
            ans[i] = q.getActivities()[i].getTitle();
        }

        //displays the question
        displayQuestion(q.getPrompt(), ans);

        //resets the previous question's elements
        resetQuestion();
    }



    /** WORK IN PROGRESS
     *  resets timer, answer boxes, and hides the corner text indicator
     */
    private void resetQuestion() {
        //need a way to reset the timebar aswell
        answerA.setDisable(false);
        answerB.setDisable(false);
        answerC.setDisable(false);
        prompt.setText("");

    }

    /**
     * displays the question and answers on the window
     * @param question: string of the question
     * @param answers: a list of strings, expected array length = 3
     */
    public void displayQuestion(String question, String[] answers){
        questionField.setText(question);
        answerA.setText(answers[0]);
        answerB.setText(answers[1]);
        answerC.setText(answers[2]);
    }


}
