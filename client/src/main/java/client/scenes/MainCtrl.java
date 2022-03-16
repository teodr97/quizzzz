package client.scenes;

import client.MyFXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

public class MainCtrl {

    private static MyFXML FXML;

    private Stage primaryStage;

    //initializes the stage and gets the scene from Splash.fxml
    //Opens/Shows the stage.
    public void initialize(Stage primaryStage, Pair<Splash, Parent> overview, MyFXML FXML) {
        this.primaryStage = primaryStage;
        MainCtrl.FXML = FXML;

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
}
