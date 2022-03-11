package commons.game;

import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Game {

    /**
     * The round the game is currently on.
     * Round 0 means the game hasn't started. (could be changed later)
     */
    private int curRound;

    /**
     * The total amount of rounds the game has.
     */
    private int totalRounds;

    /**
     * The current question given to the players.
     */
    private Question curQuestion;

    /**
     * The list of players currently playing.
     */
    private List<Player> players;

    public Game() {
        this.curRound = 0;
        this.totalRounds = 20;
        this.players = new ArrayList<>();
        this.curQuestion = null;
    }

    public int getCurRound() {
        return curRound;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public Question getCurQuestion() {
        return curQuestion;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
