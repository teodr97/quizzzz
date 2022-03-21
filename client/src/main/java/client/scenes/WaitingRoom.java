package client.scenes;

import client.MyFXML;
import client.MyModule;
import javafx.fxml.FXML;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import javafx.stage.Stage;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.google.inject.Guice.createInjector;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;


public class WaitingRoom implements Initializable {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);
    private static final String SERVER = "http://localhost:8080/";

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Stage primaryStage;

    private Scene overview;

    @FXML
    private ListView lobby;

    @Inject
    public WaitingRoom(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // code goes here.
                longpollUpdateLobby();
                renderLobby();
            }
        }).start();

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


        ArrayList<String> playersstring = playersResponse.readEntity(ArrayList.class);
        System.out.println(playersstring.toString());
        while(playersstring.size()>1){
            playersResponse = ClientBuilder.newClient(new ClientConfig()) //
                    .target(SERVER).path("/game/getPlayers/0") //
                    .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE)//
                    .request(APPLICATION_JSON) //
                    .accept(APPLICATION_JSON) //
                    .get();
            playersstring = playersResponse.readEntity(ArrayList.class);
            System.out.println(playersstring.toString());
        }



    }

    //renders the lobby in fxml file

    public void renderLobby(){
        lobby = new ListView<>();

        return;
    }

    //we switched controllers so we add the leave game methode so that the fxml won't throw an error
    // for no it's just a place holder tho because we dont have acces to the player object we want to post

    //If the event is executed then the scene switches to Splash.fxml
    public void leaveGame(ActionEvent event){
        return;

    }





}
