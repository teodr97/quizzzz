package client.scenes;

import client.MyFXML;
import client.MyModule;
import com.google.inject.Injector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.google.inject.Guice.createInjector;

public class TestMainCtrl{

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    private Stage primaryStage;
    private Stage stage;
    private Scene scene;

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




    //initializes the stage and gets the scene from Splash.fxml
    //Opens/Shows the stage.
    public void initialize(Stage primaryStage, Pair<Splash, Parent> overview) {
        this.primaryStage = primaryStage;
        this.scene = new Scene(overview.getValue());
        this.prompt = new Text();

        showPrimaryStage();
    }

    //Sets the title and scene for the Startingstage.
    public void showPrimaryStage() {
        primaryStage.setTitle("QUIZZ");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Sets and shows the scene.
    public void setAndShowScenes(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //If the event is executed then the scene switches to Splash.fxml
    public void switchToSplash(ActionEvent event) throws IOException{
        var overview = FXML.load(Splash.class, "client", "scenes", "Splash.fxml");
        scene = new Scene(overview.getValue());
        setAndShowScenes(event);
    }

    //Switches to HowToPlay.fxml
    public void switchToHowToPlay(ActionEvent event) throws IOException{
        var overview = FXML.load(HowToPlay.class, "client", "scenes", "HowToPlay.fxml");
        scene = new Scene(overview.getValue());
        setAndShowScenes(event);
    }

    //Switches to PastGames.fxml
    public void switchToPastGames(ActionEvent event) throws IOException{
        var overview = FXML.load(HowToPlay.class, "client", "scenes", "PastGames.fxml");
        scene = new Scene(overview.getValue());
        setAndShowScenes(event);
    }


    //Switches to Username.fxml
    public void switchToUsername(ActionEvent event) throws IOException{
        var overview = FXML.load(HowToPlay.class, "client", "scenes", "Username.fxml");
        scene = new Scene(overview.getValue());
        setAndShowScenes(event);
    }

    //Start single player game(for now only goes to singleplayer game screen
    public void switchToSinglePlayer(ActionEvent event) throws IOException{
        var overview = FXML.load(SinglePlayer.class, "client", "scenes", "SinglePlayer.fxml");
        scene = new Scene(overview.getValue());
        setAndShowScenes(event);
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
