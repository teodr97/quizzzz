package commons.models;

import lombok.Data;

import java.net.InetAddress;
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

    //this is  a temporary field will be changed when we generalized
    private int waitingroomid;

    //ip address
    private InetAddress address;



    public Player(){

    }

    /**
     * Player Constructor.
     * @param nickname The nickname of the player.
     */
    public Player(String nickname) {
        this.nickname = nickname;
        this.points = 0;
    }

    /**
     * Player Constructor.
     * @param nickname The nickname of the player.
     * @param address ip address of the player
     */
    public Player(String nickname, InetAddress address) {
        this.nickname = nickname;
        this.address = address;
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

    public int getWaitingRoomId(){return this.waitingroomid;}

    public void setWaitingRoomId(int id){this.waitingroomid = id;}

    /**
     * The equals method of this object.
     * @param other the object against which the instance of this class is to be compared
     * @return boolean that indicates whether the two objects are equal
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Player player = (Player) other;
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
