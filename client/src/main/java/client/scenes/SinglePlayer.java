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


    //in the fields below we hardocde the answers and questoins array
    //I think in the future it would be handy if we had a queston objects which had the actual questoin as a string
    // and then asllo it's corresponding answer. This answer should be exactly equal to the text on the button so that
    // we can check for equality between what the user pressed and what the answer is.

    //harcoded questions array this information will need to be retrieved from the database
    //I think this is done with the server field
    String[] questions = new String[4];
    Iterator<String> questionIterator= Arrays.stream(questions).iterator();

    ////harcoded answers array this information will need to be retrieved from the database
    String[] answers = new String[4];
    Iterator<String> answersIterator= Arrays.stream(answers).iterator();

    //hardcoded points array for each question so 4 entries array
    int[] points = new int[4];
    Iterator<Integer> pointsIterator= Arrays.stream(points).iterator();

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
        //the questions can also become objects in the future for now we use strings tho
        questions[0] = "Question 1";
        questions[1] = "Question 2";
        questions[2] = "Question 3";
        questions[3] = "Question 4";

        //when we create objects for the questoins each questoin will have the question string field and it's answer
        //for now tho we simulate the arbiotrary order of questions by shuffling answers
        // we want the checkanswer function to check the validity of these answers
        //to make sure this is done in a general way we shuflle these answers:
        //each corresponding to a questions see above
        answers[0] = "answerA";
        answers[1] = "answerB";
        answers[2] = "answerC";
        answers[3] = "answerA";
        List<String> list =Arrays.asList(answers);

        //Again witht he hardcoding
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




        //makes an array with references to the asnwer buttons
        answerbuttons[0]= answerA;
        answerbuttons[1] = answerB;
        answerbuttons[2] = answerC;

        Collections.shuffle(list);
        answersIterator = list.iterator();
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

    //check answers in singleplayer
    // check answer also make sures the lpayers points get updated
    public void checkAnswer(ActionEvent event) throws IOException, InterruptedException {
        //check answer will also have to call a function:
        //disableAnswers so the uses can't click the answers after already choosing one

        //get the button clicked from the event paramater
        Button useranswer = (Button) event.getTarget();

        //
        int questionpoints = pointsIterator.next();
        String correctanswer = answersIterator.next();
        System.out.println("correct answer:"+ correctanswer);
        System.out.println("your answer:"+ useranswer.getText());



        //since we made an iteroator of the asnwers the program checks if  the users button clicked is the right corresponding click
        //this function should defintely be tested

        //make the buttons there "corerct collors" green for right answer red for the wrong answers
        for(Button answerbutton: answerbuttons){
            //the one corersponding witht he next asnwers entry is the correct answer and  becomes green
            if(answerbutton.getText().equals(correctanswer)){
                answerbutton.setStyle("-fx-background-color: #309500;");
            }else{ //we make it red
                answerbutton.setStyle("-fx-background-color: #BD0000;");

            }
        }

        //after accordingly change the buttons collors we
        //we retriieve the current style of all the buttons and add a border to the user chosen button
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
        //after that we have to proompt of if the user was correct or not
        //user got the answer correct
        if(correctanswer.equals(useranswer.getText())){
            int currentpoints = Integer.parseInt(userpoint.getText());
            int newpoints = currentpoints + questionpoints;
            userpoint.setText(String.valueOf(newpoints));
            prompt.setText("Correct");
        } else{
            prompt.setText("Incorrect");
        }

        //change scene state to the one where someone has answererd the question
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
        questionField.setText(questionIterator.next());
        resetGamescreen();


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
                if(questionIterator.hasNext()){
                    nextQuestion();


                }
                //when timer ends and game hasn't ended we want to display the next question;


            }

        }
    }



}
