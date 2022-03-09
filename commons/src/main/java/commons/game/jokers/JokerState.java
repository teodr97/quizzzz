package commons.game.jokers;

public enum JokerState {
    /**
     * The joker is available to the player to be used.
     */
    Available,
    /**
     * The joker is currently in use for this round.
     */
    Active,
    /**
     * The joker has already been used.
     */
    Used
}
