package commons.game;

import java.util.ArrayList;
import java.util.List;

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

    public List<Player> getWaitingPlayers() {
        return waitingPlayers;
    }
}
