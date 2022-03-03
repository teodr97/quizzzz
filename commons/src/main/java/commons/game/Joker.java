package commons.game;

public abstract class Joker {

    /**
     * The name of the joker
     */
    private String name;
    //picture

    /**
     * Joker Constructor.
     * @param name The name of the joker.
     */
    public Joker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Uses the joker. More parameters should be added, such as the player who used it.
     */
    public abstract void use();
}
