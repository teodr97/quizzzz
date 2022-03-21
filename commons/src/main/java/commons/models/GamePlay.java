package commons.models;

import lombok.Data;

import java.util.*;

@Data
public class GamePlay {
    private String gameId;
    private List<Player> players;

    /**
     * The constructor method of this function. Mainly for testing purposes.
     */
    public GamePlay() {
        players = new LinkedList<>();
        gameId = new String("<GameId>");
    }

    /**
     *
     * @return
     */
    public String getGameId() {
        return gameId;
    }

    /**
     *
     * @param gameId
     */
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    /**
     *
     * @return
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     *
     * @param players
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
