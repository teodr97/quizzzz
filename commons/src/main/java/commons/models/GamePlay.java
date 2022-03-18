package commons.models;

import lombok.Data;

import java.util.List;

@Data
public class GamePlay {
    private String gameId;
    private List<Player> players;

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
