package commons.game;

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

    public void setCurRound(int curRound) {
        this.curRound = curRound;
    }

    /** TEMPORARY METHOD (REPLACE WITH DATABASE QUERY)
     * creates a question instance for the game
     * @return a question containing a question prompt and answers
     */
    public Question createQuestion(){

        //TEMPORARY: this whole part needs to be replaced with a database query
        Activity a = new Activity("Running a mile",1,"");
        Activity b = new Activity("Swimming a mile", 1, "");
        Activity c = new Activity("Biking a mile",1,"");
        Activity[] activityList = new Activity[]{a,b,c};

        return new Question(activityList,2,"What uses more energy?");
    }

}
