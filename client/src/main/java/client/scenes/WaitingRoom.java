package client.scenes;

import client.MyFXML;
import client.MyModule;


import javafx.fxml.FXML;


import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import jakarta.ws.rs.client.ClientBuilder;


import jakarta.ws.rs.core.Response;
import javafx.event.ActionEvent;




import javafx.fxml.Initializable;
import javafx.scene.Scene;

import javafx.scene.control.ListView;

import javafx.scene.text.Text;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import java.util.ResourceBundle;

import static com.google.inject.Guice.createInjector;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class WaitingRoom implements Initializable {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);
    private static final String SERVER = "http://localhost:8080/";

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Username usernameCtrl;

    private Scene overview;

    @FXML
    private ListView lobby;

    @FXML
    private Text players;


    /**Construtor of the waiting room scene class controller
     * @param server has the server utilities
     * @param mainCtrl the maincntrl of the application
     * @throws InterruptedException
     */
    @Inject
    public WaitingRoom(ServerUtils server, MainCtrl mainCtrl) throws InterruptedException {
        this.server = server;
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                // code goes here.
                longpollUpdateLobby();

            }
        }).start();
    }

    /**
     * If the event is executed then the scene switches to Splash.fxml
     * @param event
     * @throws IOException
     */
    public void leaveGame(ActionEvent event) throws IOException {
//        ClientBuilder.newClient(new ClientConfig()) //
//                .target(SERVER).path("/game/leave") //
//                .request(APPLICATION_JSON) //
//                .accept(APPLICATION_JSON) //
//                .post(Entity.entity(mainCtrl.getPlayer(), APPLICATION_JSON));
//
//        mainCtrl.switchToSplash();
    }

    /**
     * Keeps pinning the server for a new player list
     */
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



        ArrayList<String> playersstring = playersResponse.readEntity(ArrayList.class);

        players.setText("connected players: " + playersstring.toString());

        System.out.println(playersstring.toString());
        while(playersstring.size()>=1){
            playersResponse = ClientBuilder.newClient(new ClientConfig()) //
                    .target(SERVER).path("/game/getPlayers/0") //
                    .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE)//
                    .request(APPLICATION_JSON) //
                    .accept(APPLICATION_JSON) //
                    .get();
            playersstring = playersResponse.readEntity(ArrayList.class);
            players.setText("connected players: " + playersstring.toString());
            System.out.println(playersstring.toString());

        }



    }


    /**
     * Will start the websocket connection and thus the game
     */
    public void startGame(){

    }


    /**
     * makes you leave the game.
     */
    public void leaveGame(){
        return;
    }

    //renders the lobby in fxml fil

    //we switched controllers so we add the leave game methode so that the fxml won't throw an error
    // for no it's just a place holder tho because we dont have acces to the player object we want to post

}
