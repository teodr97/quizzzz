package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import java.net.URL;
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

    @Inject
    public WaitingRoom(ServerUtils server, MainCtrl mainCtrl) throws InterruptedException {
        this.server = server;
        this.mainCtrl = mainCtrl;

        longpollUpdateLobby();
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){


    }

    //recursive function that keeps requesting the server for new data
    //in a longpolling fashion
    public void longpollUpdateLobby() throws InterruptedException{
        //this get requests gets all the players that are connected/connecting to the server
        Response playersResponse = ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/game/getPlayers/0") //
                .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE)//
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get();
        List<String> playerslist = playersResponse.readEntity(List.class);
        System.out.println(playerslist.toString());
        // now has the updated version of the users
        // we want to "render this updated version
        renderUsernameLobby(playerslist);
        Thread.sleep(5000);
        longpollUpdateLobby();

    }


    //fucntion used to put the users on the screen
    public void renderUsernameLobby(List<String> players){

        return;
    }




}
