package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
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
import java.util.ResourceBundle;

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

    double progress;

    @FXML
    private Text questionField;

    @Inject
    public SinglePlayer(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){
        questionField.setText("Question goes here");


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

    public void increaseProgress(){

        timerBar.setStyle("-fx-accent: red");

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

    //If the event is executed then the scene switches to Splash.fxml
    public void switchToSplash(ActionEvent event) throws IOException{
        var overview = FXML.load(Splash.class, "client", "scenes", "Splash.fxml");
        scene = new Scene(overview.getValue());
        setAndShowScenes(event);
    }



}
