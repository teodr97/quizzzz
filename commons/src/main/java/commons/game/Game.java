package commons.game;

import server.ServerUtils;

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
        Question[] questions = new Question[20];
        Activity[] answers = new Activity[20];
        for(int i = 0; i < 20; i++){
            questions[i] = new Question(ServerUtils.retrieveRandomActivities());
            answers[i] = questions[i].getCorrectAnswer();
        }
        this.answers = answers;
        this.questions = questions;
    }

    /**
     * Locks in an answer chosen by the player.
     * @param player The player making a choice.
     * @param choice The choice the player made. Should be in the range
     *               [0, options) or -1 representing nothing chosen.
     * @throws IllegalChoiceException Thrown if passed a choice out of bands.
    public void pickAnswer(Player player, int choice) throws IllegalChoiceException {
        if (choice >= this.curQuestion.getOptions().length || choice < -1) throw new IllegalChoiceException();
        player.setChosenAnswer(choice);

        // calculate remaining time for player.setTimeLeft();
        // depends on how we handle the timer
    }
    */

    /** TEMPORARILY COMMENTED OUT BECAUSE WE HAVE TO CHANGE THE TYPE OF PLAYER ACTIVITY
     * Ends the question after the timer has ran out. Gives points to the correct players.

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
    */

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
        this.curQuestion = new Question(ServerUtils.retrieveRandomActivities());
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
