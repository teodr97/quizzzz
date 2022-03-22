package client.scenes;

import commons.game.exceptions.NotFoundException;
import commons.models.Game;
import commons.models.GameStorage;
import commons.models.Player;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.glassfish.jersey.client.ClientConfig;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;


public class Username implements Initializable {

    private MainCtrl mainCtrl;

    @FXML
    private TextField username;

    @FXML
    private Text missingUser;

    @Inject
    public Username(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }

    /**
     * switches to the splash screen, for the leave button
     * @param event
     * @throws IOException
     */
    public void switchToSplash(ActionEvent event) throws IOException {
        mainCtrl.switchToSplash();
    }

    /**
     * switches to the multiplayer screen, for the join game button
     * @param event
     * @throws IOException
     */
    public void switchToMultiplayer(ActionEvent event) throws IOException {
        mainCtrl.switchToMultiplayer();
    }

    /**
     * Switches to the waiting room screen.
     * @param event
     */
    public void switchToWaitingRoom(ActionEvent event) {
        if(username.getText() == null || username.getText().equals("")){
            missingUser.setText("please enter a username");
        }else if(usernameTaken(username.getText())){
            missingUser.setText("username already taken");
        }
        else{
            mainCtrl.setPlayer(new Player(username.getText()));
            Response response = ClientBuilder.newClient(new ClientConfig()) //
                    .target(mainCtrl.SERVER).path("/game/connect") //
                    .request(APPLICATION_JSON) //
                    .accept(APPLICATION_JSON) //
                    .post(Entity.entity(mainCtrl.getPlayer(), APPLICATION_JSON));
            String responsestring = response.readEntity(String.class);
            System.out.println(responsestring);
            mainCtrl.switchToWaitingRoom();
        }
    }

    public boolean usernameTaken(String username){
        Game game = GameStorage.getInstance().getGames().values().stream()
                .filter(it -> it.contains(username))
                .findAny().orElse(null);
        if(game != null) return true;
        return false;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return this.username.getText();
    }
}
