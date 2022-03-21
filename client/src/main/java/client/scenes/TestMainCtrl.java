package client.scenes;

import client.MyFXML;
import client.MyModule;
import com.google.inject.Injector;
import commons.models.Player;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import javafx.fxml.FXML;

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
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import java.io.IOException;

import static com.google.inject.Guice.createInjector;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class TestMainCtrl{

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);
    private static final String SERVER = "http://localhost:8080/";

    private Stage primaryStage;
    private Stage stage;
    private Scene scene;
    private Player player;

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

    @FXML
    private TextField username;

    @FXML
    private Button startGame;



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

    //Switches to WaitingRoom.fxml
    public void switchToWaitingRoom(ActionEvent event) throws IOException, InterruptedException{
        var overview = FXML.load(HowToPlay.class, "client", "scenes", "WaitingRoom.fxml");
        this.player = new Player(username.getText());

        //this request sends the player info to the server
        Response response = ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/game/connect") //
                .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE)//
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(player, APPLICATION_JSON));
        String responsestring = response.readEntity(String.class);
        System.out.println(responsestring);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // code goes here.
                longpollUpdateLobby();
            }
        }).start();

        scene = new Scene(overview.getValue());
        setAndShowScenes(event);
    }

    //recursive function that keeps requesting the server for new data
    //in a longpolling fashion
    public void longpollUpdateLobby(){
        //this get requests gets all the players that are connected/connecting to the server
        Response playersResponse = ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/game/getPlayers/0") //
                .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE)//
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get();
        String playersstring = playersResponse.readEntity(String.class);
        System.out.println(playersstring);

        longpollUpdateLobby();

    }

    //If the event is executed then the scene switches to Splash.fxml
    public void leaveGame(ActionEvent event) throws IOException{
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/game/leave") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(player, APPLICATION_JSON));
        var overview = FXML.load(Splash.class, "client", "scenes", "Splash.fxml");
        scene = new Scene(overview.getValue());
        setAndShowScenes(event);
    }

    //Start single player game(for now only goes to singleplayer game screen
    public void switchToSinglePlayer(ActionEvent event) throws IOException{
        var overview = FXML.load(SinglePlayer.class, "client", "scenes", "SinglePlayer.fxml");
        scene = new Scene(overview.getValue());
        //questionField.setText("What what what is this world?");

        setAndShowScenes(event);

        //questionField.setText("What what what is this world?");
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
