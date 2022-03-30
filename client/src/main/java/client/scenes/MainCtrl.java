package client.scenes;

import client.MyFXML;

import commons.models.Game;
import commons.models.Player;


import javafx.application.Platform;
import javafx.fxml.FXML;


import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.scene.control.TextField;

import javafx.stage.Stage;
import javafx.util.Pair;




import java.io.IOException;




public class MainCtrl {

    public static final String SERVER = "http://localhost:8080/";
    private static MyFXML myFXML;
    private Player player;
    private Game game;

    private Stage primaryStage;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField username;

    private WaitingRoom room;
    private Scene waitingRoom;


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
     * Sets the game object.
     * @param game The game that will be set through Username class.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Gets the player object.
     * @return
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * Initialises the starting stage of the application.
     * @param primaryStage The primary stage used throughout the lifespan of the app.
     * @param overview Overview of the Splash scene.
     * @param myFXML The FXML injector used throughout the lifespan of the app.
     */
    public void initialize(Stage primaryStage, Pair<WaitingRoom, Parent> waitingRoom, Pair<Splash, Parent> overview, MyFXML myFXML) {
        this.primaryStage = primaryStage;
        MainCtrl.myFXML = myFXML;

        this.room = waitingRoom.getKey();
        this.waitingRoom = new Scene(waitingRoom.getValue());

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
     * Switches the scene to the Single Player username scene where the player can enter their username.
     */
    public void switchToSingleplayerUsername() {
        var overview = myFXML.load(SingleplayerUsername.class, "client", "scenes", "SingleplayerUsername.fxml");
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
        try{
            // in the meantime we created a websocket thread which where we try to call the swithtoMultplayer function from
            //since java fx needs to be run in it's own thread w
            // e need the Playform.runlater block
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    setAndShowScenes(new Scene(overview.getValue()));
                }
            });

        }catch(Exception e){

            System.out.println(e.getMessage());
        }
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
    public void switchToWaitingRoom(){
        setAndShowScenes(waitingRoom);
    }

    /**
     * Starts the long polling in waitingroom.
     */
    public void startLongPolling(){
        room.joinGame(player);
        room.longpollUpdateLobby(player);
    }

    /**
     * Stops the thread in waitingroom when application is closed.
     */
    public void stopThread(){
        room.leaveGame();
    }

    /**
     * Switches the scene to the admin panel
     */
    public void switchToAdmin() {
        var overview = myFXML.load(WaitingRoom.class, "client", "scenes", "Admin.fxml");
        setAndShowScenes(new Scene(overview.getValue()));
    }

}
