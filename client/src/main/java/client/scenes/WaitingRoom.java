package client.scenes;

import client.MyFXML;
import client.MyModule;


import commons.models.Game;
import commons.models.Player;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import javafx.fxml.FXML;



import com.google.inject.Inject;
import com.google.inject.Injector;
import jakarta.ws.rs.client.ClientBuilder;


import javafx.event.ActionEvent;




import javafx.fxml.Initializable;
import javafx.scene.Scene;

import javafx.scene.control.ListView;

import javafx.scene.text.Text;

import javafx.scene.text.TextAlignment;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.inject.Guice.createInjector;
import static commons.models.GameStatus.ONGOING;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class WaitingRoom implements Initializable {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);
    private static final String SERVER = "http://localhost:8080/";
    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();
    private static final ExecutorService EXEC2 = Executors.newSingleThreadExecutor();


    private final MainCtrl mainCtrl;

    private Username usernameCtrl;

    private Scene overview;

    @FXML
    private ListView lobby;

    @FXML
    private Text players;


    /**Construtor of the waiting room scene class controller
     * @param mainCtrl the maincntrl of the application
     * @throws InterruptedException
     */
    @Inject
    public WaitingRoom(MainCtrl mainCtrl) throws InterruptedException {

        this.mainCtrl = mainCtrl;

        this.players = new Text();
    }

    //no real functionality yet

    /**
     * @param location url we use to acces scene
     * @param resources resources used for scene
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Sends a request to start the game. Sends the player who started as the parameter.
     * Game will be set to ongoing state.
     * @param event
     * @throws IOException
     */
    public void startGame(ActionEvent event) throws IOException{
        ClientBuilder.newClient(new ClientConfig()) //
                .target(mainCtrl.SERVER).path("/game/start") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(this.mainCtrl.getPlayer(), APPLICATION_JSON));

        this.mainCtrl.switchToMultiplayer();
    }

    /**
     * If the event is executed then the scene switches to Splash.fxml
     * @param event
     * @throws IOException
     */
    public void leaveGame(ActionEvent event) throws IOException {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/game/leave")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
               .post(Entity.entity(mainCtrl.getPlayer(), APPLICATION_JSON));

        this.mainCtrl.switchToSplash();
    }

    /**
     * Checks if a game was started if yes switches screen.
     * @param player
     */
    public void joinGame(Player player){
        //checks if the game the player is in is started or not
        EXEC2.submit(() -> {
            while(!Thread.interrupted()){
                Game game = ClientBuilder.newClient(new ClientConfig()) //
                        .target(SERVER).path("/game/getGame") //
                        .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE)
                        .queryParam("player", player.getNickname())//
                        .request(APPLICATION_JSON) //
                        .accept(APPLICATION_JSON) //
                        .get(new GenericType<Game>() {});
                if(game.getStatus().equals(ONGOING)){
                    System.out.println("Heyho" + player.getNickname());
                    this.mainCtrl.switchToMultiplayer();
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    EXEC2.shutdownNow();
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Keeps pinning the server for a new player list
     */
    //recursive function that keeps requesting the server for new data
    //in a longpolling fashion
    public void longpollUpdateLobby(Player player){
        //this get requests gets all the players that are connected/connecting to the server
        EXEC.submit(() -> {
            while(!Thread.interrupted()){
                ArrayList<Player> playersResponse = ClientBuilder.newClient(new ClientConfig()) //
                        .target(SERVER).path("/game/getPlayers") //
                        .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE)
                        .queryParam("player", player.getNickname())//
                        .request(APPLICATION_JSON) //
                        .accept(APPLICATION_JSON) //
                        .get(new GenericType<ArrayList<Player>>() {});

                updatePlayerListText(playersResponse, players);
                System.out.println(player.getNickname() + "!!!!!!!!!!!!!!");
                System.out.println(playersResponse.toString());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    EXEC.shutdownNow();
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getState());
        });
    }


    /**
     * Will start the websocket connection and thus the game
     */
    public void startGame(){

    }

    /**
     * Updates the text displaying the list of players currently in lobby.
     * @param playersString the list of the usernames of players waiting in the lobby
     * @param playersText the text displaying the list of player usernames
     */
    private void updatePlayerListText(ArrayList<Player> playersString, Text playersText) {
        String output = "Players in lobby:\n";

        for (Player player : playersString) {
            output += "\u2022 " + player.getNickname() + "\n";
        }
        playersText.setTextAlignment(TextAlignment.LEFT);
        playersText.setText(output);
    }

    /**
     * makes you leave the game.
     */
    public void leaveGame(){
        return;
    }

    /**
     * Stops thread
     */
    public void stop() {
        EXEC2.shutdownNow();
        EXEC.shutdownNow();
    }

    //renders the lobby in fxml fil

    //we switched controllers so we add the leave game methode so that the fxml won't throw an error
    // for no it's just a place holder tho because we dont have acces to the player object we want to post

}
