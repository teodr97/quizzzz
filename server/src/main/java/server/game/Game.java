package server.game;

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

    /**
     * Locks in an answer chosen by the player.
     * @param player The player making a choice.
     * @param choice The choice the player made. Should be in the range
     *               [0, options) or -1 representing nothing chosen.
     * @throws IllegalChoiceException Thrown if passed a choice out of bands.
     */
    public void pickAnswer(Player player, int choice) throws IllegalChoiceException {
        if (choice >= this.curQuestion.getOptions().length || choice < -1) throw new IllegalChoiceException();
        player.setChosenAnswer(choice);

        // calculate remaining time for player.setTimeLeft();
        // depends on how we handle the timer
    }

    /**
     * Ends the question after the timer has ran out. Gives points to the correct players.
     */
    public void endQuestion() {
        for (var player : this.players) {
            if (player.getChosenAnswer() == this.curQuestion.getAnswer()) {
                double timePoints = 300*player.getTimeLeft();
                int gainedPoints = 500 + (int)timePoints;
                //should check for a joker active;
                player.addPoints(gainedPoints);
            }
        }
    }

    /**
     * Starts a new round of the game. Increments the curRound counter.
     * If the game has already ended it instead calls Game.endGame();
     */
    public void startNextRound() {
        if (this.curRound >= this.totalRounds) {
            this.endGame();
            return;
        }

        //resetting player choices
        for (var player : this.players) {
            player.setChosenAnswer(-1);
            player.setTimeLeft(1);
        }
        this.curQuestion = Question.generateQuestion();
        //reset time

        this.curRound++;
    }

    /**
     * Ends the game.
     */
    public void endGame() {

    }

    private class IllegalChoiceException extends Throwable {

    }
}
