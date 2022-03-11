package commons;

import lombok.Data;

import java.util.List;

@Data
public class GamePlay {
    private String gameId;
    private List<Player> players;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
