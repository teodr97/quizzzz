package client.scenes;

import com.google.inject.Inject;
import commons.models.Player;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import org.glassfish.jersey.client.ClientConfig;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;


public class WaitingRoom implements Initializable {

    private final MainCtrl mainCtrl;

    private Player player;

    private Username usernameCtrl;

    @Inject
    public WaitingRoom(MainCtrl mainCtrl, Username usernameCtrl) {
        this.mainCtrl = mainCtrl;
        this.usernameCtrl = usernameCtrl;
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){
        this.player = new Player(usernameCtrl.getUsername());
        Response response = ClientBuilder.newClient(new ClientConfig()) //
                .target(mainCtrl.SERVER).path("/game/connect") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(this.player, APPLICATION_JSON));
        String responsestring = response.readEntity(String.class);
        System.out.println(responsestring);
    }

    /**
     * If the event is executed then the scene switches to Splash.fxml
     * @param event
     * @throws IOException
     */
    public void leaveGame(ActionEvent event) throws IOException {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(mainCtrl.SERVER).path("/game/leave") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete().close();

        mainCtrl.switchToSplash();
    }
}
