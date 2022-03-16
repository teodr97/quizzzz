package client.scenes;

import client.MyFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

public class TestMainCtrl{

    private static MyFXML FXML;

    private Stage primaryStage;

    @FXML
    private Button answerA;

    @FXML
    private Button answerB;

    @FXML
    private Button answerC;

    @FXML
    private Text prompt;
    @FXML
    private ProgressBar timerBar;

    @FXML
    private Text questionField;


    //initializes the stage and gets the scene from Splash.fxml
    //Opens/Shows the stage.
    public void initialize(Stage primaryStage, Pair<Splash, Parent> overview, MyFXML FXML) {
        this.primaryStage = primaryStage;
        this.prompt = new Text();
        TestMainCtrl.FXML = FXML;

        primaryStage.setTitle("QUIZZ");
        primaryStage.setScene(new Scene(overview.getValue()));
        primaryStage.show();
    }

    //Sets and shows the scene.
    public void setAndShowScenes(Scene scene){
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //If the event is executed then the scene switches to Splash.fxml
    public void switchToSplash() {
        var overview = FXML.load(Splash.class, "client", "scenes", "Splash.fxml");
        setAndShowScenes(new Scene(overview.getValue()));
    }

    //Switches to HowToPlay.fxml
    public void switchToHowToPlay() {
        var overview = FXML.load(HowToPlay.class, "client", "scenes", "HowToPlay.fxml");
        setAndShowScenes(new Scene(overview.getValue()));
    }

    //Switches to PastGames.fxml
    public void switchToPastGames() {
        var overview = FXML.load(PastGames.class, "client", "scenes", "PastGames.fxml");
        setAndShowScenes(new Scene(overview.getValue()));
    }

    //Switches to Username.fxml
    public void switchToUsername() {
        var overview = FXML.load(Username.class, "client", "scenes", "Username.fxml");
        setAndShowScenes(new Scene(overview.getValue()));
    }

    //Start single player game(for now only goes to singleplayer game screen
    public void switchToSinglePlayer() {
        var overview = FXML.load(SinglePlayer.class, "client", "scenes", "SinglePlayer.fxml");
        setAndShowScenes(new Scene(overview.getValue()));
    }


    /**
     * Switches the scene to the end-screen for singleplayer.
     * @throws IOException
     */
    public void switchToEndscreenSingleplayer() {
        var overview = FXML.load(EndscreenSingleplayer.class, "client", "scenes", "EndscreenSingleplayer.fxml");
        setAndShowScenes(new Scene(overview.getValue()));
    }

    /**
     * Switches the scene to the end-screen for multiplayer.
     * @throws IOException
     */
    public void switchToEndscreenMultiplayer() {
        var overview = FXML.load(EndscreenMultiplayer.class, "client", "scenes", "EndscreenSingleplayer.fxml");
        setAndShowScenes(new Scene(overview.getValue()));
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
    public void showCorrect() throws IOException {


        answerA.setDisable(true);
        answerA.setStyle("-fx-background-color: #309500");
        answerA.setDisable(true);
        answerB.setStyle("-fx-background-color: #BD0000");

        answerC.setDisable(true);
        answerC.setStyle("-fx-background-color: #BD0000");
        return;
    }




}
