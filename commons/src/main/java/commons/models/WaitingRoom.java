package commons.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WaitingRoom {

    /**
     * The waiting players in the lobby.
     */
    private List<Player> waitingPlayers;

    /**
     * Waiting Room Constructor.
     */
    public WaitingRoom() {
        this.waitingPlayers = new ArrayList<>();
    }

    public void AddPlayer(Player player){
        waitingPlayers.add(player);
    }

    public List<Player> getWaitingPlayers() {
        return this.waitingPlayers;
    }
}
