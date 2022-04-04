package client.scenes;

import commons.models.Game;
import commons.models.GameStorage;
import commons.models.Player;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;


public class Username implements Initializable {

    private MainCtrl mainCtrl;
    private static ExecutorService exec = Executors.newSingleThreadExecutor();
    private ServerSelectorCtrl serverSelectorCtrl;

    @FXML
    private TextField username;

    @FXML
    private Text missingUser;

    @FXML
    private Text activePlayers;

    @Inject
    public Username(MainCtrl mainCtrl, ServerSelectorCtrl serverSelectorCtrl) {
        this.mainCtrl = mainCtrl;
        this.serverSelectorCtrl = serverSelectorCtrl;
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
        }
        else{
            mainCtrl.setPlayer(new Player(username.getText()));
            Response response = ClientBuilder.newClient(new ClientConfig()) //
                    .target(serverSelectorCtrl.getServer()).path("/game/connect") //
                    .request(APPLICATION_JSON) //
                    .accept(APPLICATION_JSON) //
                    .post(Entity.entity(mainCtrl.getPlayer(), APPLICATION_JSON));
            String responsestring = response.readEntity(String.class);
            System.out.println(responsestring);
            System.out.println(response.getStatus());
            if(response.getStatus() == 500){
                missingUser.setText("Username is already taken");
            }else{
                stop();
                mainCtrl.startLongPolling();
                mainCtrl.switchToWaitingRoom();
            }
        }
    }

    /**
     * Thread for getting active players.
     */
    public void longpollUpdateLobby(){
        //this get requests gets all the players that are connected/connecting to the server
        System.out.println("Before thread.");
        exec = Executors.newSingleThreadExecutor();
        exec.submit(() -> {
            while(!Thread.interrupted()){
                System.out.println("Starting thread2.");
                int playersResponse = ClientBuilder.newClient(new ClientConfig()) //
                        .target(serverSelectorCtrl.getServer()).path("/game/getAllPlayers") //
                        .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE)
                        .request(APPLICATION_JSON) //
                        .accept(APPLICATION_JSON) //
                        .get(new GenericType<Integer>() {});
                System.out.println("After response.");
                updatePlayerListText(playersResponse);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    exec.shutdownNow();
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getState());
        });
    }

    private void updatePlayerListText(int num) {
        this.activePlayers.setTextAlignment(TextAlignment.LEFT);
        this.activePlayers.setText(num + " active players");
    }

    /**
     * Checks whether the username is already used by other player.
     * @param username
     * @return true or false
     */
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

    /**
     * Stops thread
     */
    public void stop() {
        exec.shutdownNow();
    }
}
