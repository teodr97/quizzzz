package client.scenes;

import commons.models.Player;
import com.google.inject.Inject;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static java.lang.String.valueOf;


public class EndscreenMultiplayer implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerSelectorCtrl serverSelectorCtrl;

    @FXML
    private Text first;
    @FXML
    private Text second;
    @FXML
    private Text third;
    @FXML
    private Text place;
    @FXML
    private Text points;

    @Inject
    public EndscreenMultiplayer(MainCtrl mainCtrl, ServerSelectorCtrl serverSelectorCtrl) throws InterruptedException {
        System.out.println("before");
        this.mainCtrl = mainCtrl;
        this.serverSelectorCtrl = serverSelectorCtrl;

    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){
        System.out.println("before");
            getPlayers();
    }

    /**
     * Gets the players that are in the same game as the player.
     */
    private void getPlayers(){
        System.out.println("before");
        ArrayList<Player> playersResponse;
        playersResponse = ClientBuilder.newClient(new ClientConfig()) //
                .target(serverSelectorCtrl.getServer()).path("/game/getPlayers") //
                .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE)
                .queryParam("player", mainCtrl.getPlayer().getNickname())//
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<ArrayList<Player>>() {});
        System.out.println("asdfsdfsfsd" + playersResponse.toString());
        leaderBoard(playersResponse);
    }

    /**
     * Orders the players in decreasing order.
     */
    private Player[] leaderBoard(List<Player> players){
        Player[] list = new Player[players.size()];
        int max = Integer.MIN_VALUE;
        int index = 0;
        for(int i = 0; i < players.size(); i++){
            for(int o = 0; o < players.size(); o++){
                if(players.get(o).getNickname() == mainCtrl.getPlayer().getNickname()){
                    points.setText("Your points: "+valueOf(players.get(o).getPoints()));
                }
                if(players.get(o).getPoints() > max){
                    max = players.get(o).getPoints();
                    index = o;
                }
            }
            max = Integer.MIN_VALUE;
            list[i] = players.get(index);
        }

        if(list.length > 0){
            first.setText(list[0].getNickname());
        }
        if(list.length > 1){
            second.setText(list[1].getNickname());
        }
        if(list.length > 3){
            third.setText(list[3].getNickname());
        }
        for(int i = 0; i < list.length; i++){
            if(list[i].getNickname() == mainCtrl.getPlayer().getNickname()){
                place.setText("Your placement: " + valueOf(i)+".");
            }
        }

        return list;
    }

}
