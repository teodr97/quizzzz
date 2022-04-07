
package commons.models;

import commons.game.Activity;


import commons.game.exceptions.NicknameTakenException;
import commons.game.exceptions.NotFoundException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Game {

    /**
     * We need a gameId for each game.
     */
    private String gameID;

    /**
     *
     * @return
     */
    public String getGameID() {
        return gameID;
    }

    /**
     *
     * @param gameID
     */
    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    /**
     * Status of the game. (Started, ended)
     */
    private GameStatus status;


    /**
     *
     * @return
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(GameStatus status) {
        this.status = status;
    }

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
     *
     * @param players
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    /**
     * The list of questions of the game
     */
    public Question[] questions;

    public Game() {
        this.curRound = 1;
        this.totalRounds = 20;
        this.players = new ArrayList<>();
        this.curQuestion = null;
    }

    /**
     * Gets the current round.
     * @return
     */
    public int getCurRound() {
        return curRound;
    }

    /**
     * Cur Round setter.
     * @param newCurRound
     */
    public void setCurRound(int newCurRound) {
        this.curRound = newCurRound;
    }

    /**
     * Gets the total amounts of the game.
     * @return
     */
    public int getTotalRounds() {
        return totalRounds;
    }

    /**
     * Gets the current question for the current round.
     * @return
     */
    public Question getCurQuestion() {
        return curQuestion;
    }

    /**
     * Sets the current question of this game.
     * @param question the question to be set as current
     */
    public void setCurQuestion(Question question) { this.curQuestion = question; }

    /**
     * Gets the list of players participating in the game.
     * @return
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Adds a player to the game.
     * @param player The player to add.
     * @throws NicknameTakenException If the player's nickname is already taken this will be thrown
     */
    public void addPlayer(Player player) throws NicknameTakenException {
        if(!contains(player.getNickname())){
            players.add(player);
            System.out.println(players.size());
            System.out.println(players.toString());
        } else throw new NicknameTakenException("Username is already used by another player!");
    }

    /**
     * Removes a player from the game. The removal will be done based on the nickname, which is unique for each player.
     * @param player
     * @throws NotFoundException Thrown if no player with the given nickname is in the game.
     */
    public void removePlayer(Player player) throws NotFoundException {
        if(contains(player.getNickname())){
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getNickname().equals(player.getNickname())){
                    players.remove(i);
                }
            }
            System.out.println("Player actually removed!");
            System.out.println(players.size());
            System.out.println(players.toString());
        } else throw new NotFoundException("Username not found!");
    }

    /**
     * Checks if a current nickname is present within the game's players.
     * @param nickname
     * @return
     */
    public Boolean contains(String nickname){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getNickname().equals(nickname)){
                return true;
            }
        }
        return false;
    }

    /**
     * creates a list of 20 questions and a list of 20 answers, assigning them to the game class variables
     */

    //im gonna change this a bit since we also need the game object for the server side of things
    //when doing multiplayer, so instead of taking as input the question generator
    // we only use the questionGenerator once and it is to retrieve  a list
    // so to use this server side i will make it so that the parammter is the lsit
    //now we basicly make a request in this class and so
    public void createQuestionList(ArrayList<Activity> alist){
        Question[] questions = new Question[this.totalRounds];
        System.out.println("=============QUESTIONS AND ANSWERS===============");
        for(int i = 0; i < totalRounds; i++){
            Question createdq = new Question(alist);
            createdq.setQuestionNo(i+1);
            questions[i] = createdq;


            System.out.println(questions[i].toString());
            
            //System.out.println(questions[i].toString());
        }
        System.out.println("=================================================");
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
     * Ends the game.
     */
    public void endGame() {

    }

    /**
     * Exception we might want to throw
     */
    private class IllegalChoiceException extends Throwable {

    }

    public interface QuestionGenerator {
        /**
         * Retrieves the set of all activities from the server.
         * @return
         */
        List<Activity> retrieveActivitySetFromServer();

        /**
         * @return all activities from the server
         */
        ArrayList<Activity> retrieveAllActivitySetFromServer();

    }
}
