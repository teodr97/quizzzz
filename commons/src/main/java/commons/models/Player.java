package commons.models;

import commons.game.Emote;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Player {
    /**
     * The nickname of the player.
     */
    private String nickname;

    /**
     * How many points the player currently has in the game.
     */
    private int points;
    // joker

    /**
     * The answer the player has chosen for the current question.
     * Corresponds to the index of the question.
     * -1 means that no answer has been chosen.
     */
    private int chosenAnswer = -1;

    /**
     * Time left on the clock when the current question was answered.
     * The time is represented as a value from 0 to 1, 0 meaning no time left and 1 that all time is left.
     */
    private double timeLeft = 1;

    /**
     * List of emotes that have been sent to the server but not sent back to all the players.
     */
    private List<Emote> pendingEmotes;

    public Player(){
        this.pendingEmotes = new ArrayList<>();
    }

    /**
     * Player Constructor.
     * @param nickname The nickname of the player.
     */
    public Player(String nickname) {
        this();
        this.nickname = nickname;
        this.points = 0;
    }

    /**
     * Gets the player nickname.
     * @return
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Gets the amount of points the player currently has.
     * @return
     */
    public int getPoints() {
        return points;
    }

    /**
     * Gives the player more points. Used at the end of the round.
     * @param gainedPoints New points from the current round.
     */
    public void addPoints(int gainedPoints) {
        this.points += gainedPoints;
    }

    /**
     * Gets the answer currently chosen by the player.
     * @return A number in the range [0, amount of answers)
     */
    public int getChosenAnswer() {
        return this.chosenAnswer;
    }

    /**
     * Sets the answwer the players wants to choose.
     * @param choice A number in the range [0, amount of answers)
     */
    public void setChosenAnswer(int choice) {
        this.chosenAnswer = choice;
    }

    /**
     * Gets how much the player has left for the answer. Should be removed/changed.
     * @return
     */
    public double getTimeLeft() {
        return timeLeft;
    }

    /**
     *
     * @param timeLeft
     */
    public void setTimeLeft(double timeLeft) {
        this.timeLeft = timeLeft;
    }

    /**
     * Adds a new emote that was sent by a player recently.
     * @param emote The emote sent.
     */
    public void addEmote(Emote emote) {
        this.pendingEmotes.add(emote);
    }

    /**
     * Gets all emotes that have been sent from other players to the backend but not received
     * by the client.
     * @return The list of emotes.
     */
    public List<Emote> getPendingEmotes() {
        return this.pendingEmotes;
    }

    /**
     * Resets the pending emotes for the player. Replaces the field with a new instances
     * so it doesn't delete the old one.
     */
    public void resetPendingEmotes() {
        this.pendingEmotes = new ArrayList<>();
    }

    /**
     * The equals method of this object.
     * @param o the object against which the instance of this class is to be compared
     * @return boolean that indicates whether the two objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return points == player.points && nickname.equals(player.nickname);
    }

    /**
     * The hash method of this object.
     * @return a hash of the instance of this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(nickname, points);
    }
}
