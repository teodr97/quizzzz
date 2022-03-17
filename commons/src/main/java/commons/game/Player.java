package commons.game;

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
     * Player Constructor.
     * @param nickname The nickname of the player.
     */
    public Player(String nickname) {
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
}
