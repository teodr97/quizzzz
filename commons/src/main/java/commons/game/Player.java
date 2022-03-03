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
     * Player Constructor.
     * @param nickname The nickname of the player.
     */
    public Player(String nickname) {
        this.nickname = nickname;
        this.points = 0;
    }

    public String getNickname() {
        return nickname;
    }

    public int getPoints() {
        return points;
    }
}
