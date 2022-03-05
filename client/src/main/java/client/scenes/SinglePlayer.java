package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
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


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
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


    //
    String[] questions = new String[2];
    Iterator<String> questionIterator= Arrays.stream(questions).iterator();

    // amount of question asked;
    int qnumber;


    @Inject
    public SinglePlayer(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){
        // we will probably retrieve the questions from the this.server variable but
        //for now we hardcode the questions to figure out functionalityu first
        //the questions can also become objects in the future for now we use strings tho
        questions[0] = "Question 1";
        questions[1] = "Question 2";
        questionField.setText(questionIterator.next());
        Timer timerobj = new Timer();
        qnumber = 0;
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
        //in which case the buttons should be disabled and change colors

        //now answer is hardcoded to be A that's why A becomes green and the rest red
        Disableanswers();
        answerB.setStyle("-fx-background-color: #BD0000;");

        answerC.setStyle("-fx-background-color: #BD0000;");




        System.out.println("user choose answer");

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
        //mainly resetting the answer buttons
        //color and clickability
        answerA.setStyle("-fx-background-color: #0249bd;");
        answerB.setStyle("-fx-background-color: #0249bd;");

        answerC.setStyle("-fx-background-color: #0249bd;");

        Enableanswers();


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
        private void handlee() {
            //making this smaller will slow down the times
            progress += 0.01;
            //set the new progress
            timerBar.setProgress(progress);
            //checks if the progress is 1 and will display prompt accordingly
            // will also diable the buttons if the timer ends
            if((timerBar.getProgress() + EPSILON > 1 && timerBar.getProgress() - EPSILON <1)){
                qnumber += 1;
                prompt.setText("Timer over");
                //when timer ends and game hasn't ended we want to display the next question
                Disableanswers();



            }

            if((timerBar.getProgress() + EPSILON > 1.5 && timerBar.getProgress() - EPSILON <1.5)){

                //when timer ends and game hasn't ended we want to display the next question;
                while(questionIterator.hasNext()){
                    questionField.setText(questionIterator.next());
                    resetGamescreen();

                }
                //when timer ends and game hasn't ended we want to display the next question;


            }

        }
    }



}
