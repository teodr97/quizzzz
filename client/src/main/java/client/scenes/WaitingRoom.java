package client.scenes;

import com.google.inject.Inject;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import org.glassfish.jersey.client.ClientConfig;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;


public class WaitingRoom implements Initializable {

    private final MainCtrl mainCtrl;

    private Username usernameCtrl;

    @FXML
    private Text waitingPlayers;

    @Inject
    public WaitingRoom(MainCtrl mainCtrl, Username usernameCtrl) {
        this.mainCtrl = mainCtrl;
        this.usernameCtrl = usernameCtrl;
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* Not working properly yet Game game = GameStorage.getInstance().getGames().values().stream()
                .filter(it -> it.getStatus().equals(WAITING))
                .findFirst().orElse(game = new Game());
        waitingPlayers.setText(game.getPlayers().size() + " players waiting");*/
    }

    public void startGame(ActionEvent event) throws IOException{
        ClientBuilder.newClient(new ClientConfig()) //
                .target(mainCtrl.SERVER).path("/game/start") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(mainCtrl.getPlayer(), APPLICATION_JSON));

        mainCtrl.switchToMultiplayer();
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
                .post(Entity.entity(mainCtrl.getPlayer(), APPLICATION_JSON));

        mainCtrl.switchToSplash();
    }
}
