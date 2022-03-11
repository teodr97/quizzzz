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

    /**
     * The list of questions of the game
     */
    public Question[] questions;

    /**
     * The list of activities that are the answers to the questions
     */
    public Activity[] answers;

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


    /**
     * creates a list of 20 questions and a list of 20 answers, assigning them to the game class variables
     */
    public void createQuestionList(){
        Question<Activity>[] questions = new Question[20];
        Activity[] answers = new Activity[20];
        for(int i = 0; i < 20; i++){
            questions[i] = Question.createQuestion();
            answers[i] = questions[i].getAnswer();
        }
        this.answers = answers;
        this.questions = questions;
    }

}
