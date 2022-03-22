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


public class MainCtrl {

    public static final String SERVER = "http://localhost:8080/";
    private static MyFXML myFXML;
    private Player player;

    private Stage primaryStage;
    private Stage stage;
    private Scene scene;
    public Player player;

    public MainCtrl() {
    }

    /**
     * Sets the player object.
     * @param player The player that will be set through Username class.
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * Gets the player object.
     */
    public Player getPlayer(){
        return this.player;
    }


    /**
     * Initialises the starting stage of the application.
     * @param primaryStage The primary stage used throughout the lifespan of the app.
     * @param overview Overview of the Splash scene.
     * @param myFXML The FXML injector used throughout the lifespan of the app.
     */
    public void initialize(Stage primaryStage, Pair<Splash, Parent> overview, MyFXML myFXML) {
        this.primaryStage = primaryStage;
        MainCtrl.myFXML = myFXML;

        primaryStage.setTitle("QUIZZ");
        primaryStage.setScene(new Scene(overview.getValue()));
        primaryStage.show();
    }

    //Sets and shows the scene.

    /**
     * Sets the primary stage to a given scene.
     * @param scene The scene that will be shown next.
     */
    public void setAndShowScenes(Scene scene){
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Switches the scene to Splash.
     */
    public void switchToSplash() {
        var overview = myFXML.load(Splash.class, "client", "scenes", "Splash.fxml");
        setAndShowScenes(new Scene(overview.getValue()));
    }

    /**
     * Switches the scene to the How To Play scene.
     */
    public void switchToHowToPlay() {
        var overview = myFXML.load(HowToPlay.class, "client", "scenes", "HowToPlay.fxml");
        setAndShowScenes(new Scene(overview.getValue()));
    }

    /**
     * Switches the scene to the list of Past Games.
     */
    public void switchToPastGames() {
        var overview = myFXML.load(PastGames.class, "client", "scenes", "PastGames.fxml");
        setAndShowScenes(new Scene(overview.getValue()));
    }

    /**
     * Switches the scene to the Username selection scene.
     */
    public void switchToUsername() {
        var overview = myFXML.load(Username.class, "client", "scenes", "Username.fxml");
        setAndShowScenes(new Scene(overview.getValue()));
    }

    /**
     * Switches the scene to the Single Player scene that also starts a new Single Player game.
     */
    public void switchToSinglePlayer() {
        var overview = myFXML.load(SinglePlayer.class, "client", "scenes", "SinglePlayer.fxml");
        setAndShowScenes(new Scene(overview.getValue()));
    }

    /**
     * Switches to the multiplayer scene after the add name button is clicked
     */
    public void switchToMultiplayer() {
        var overview = myFXML.load(MultiPlayer.class, "client", "scenes", "MultiPlayer.fxml");
        setAndShowScenes(new Scene(overview.getValue()));
    }


    /**
     * Switches the scene to the end-screen for singleplayer.
     * @throws IOException
     */
    public void switchToEndscreenSingleplayer() {
        var overview = myFXML.load(EndscreenSingleplayer.class, "client", "scenes", "EndscreenSingleplayer.fxml");
        setAndShowScenes(new Scene(overview.getValue()));
    }

    /**
     * Switches the scene to the end-screen for multiplayer.
     * @throws IOException
     */
    public void switchToEndscreenMultiplayer() {
        var overview = myFXML.load(EndscreenMultiplayer.class, "client", "scenes", "EndscreenSingleplayer.fxml");
        setAndShowScenes(new Scene(overview.getValue()));
    }

    /**
     * Switches the scene to the waiting room scene for multiplayer.
     */
    public void switchToWaitingRoom(ActionEvent event) throws IOException, InterruptedException{

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

        var overview = FXML.load(WaitingRoom.class, "client", "scenes", "WaitingRoom.fxml");
        scene = new Scene(overview.getValue());
        setAndShowScenes(event);


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
}
