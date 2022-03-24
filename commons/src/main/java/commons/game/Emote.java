package commons.game;

import java.util.Objects;

public class Emote {

    //TODO image
    private String sender;

    /**
     * Emote constructor.
     * @param sender The nickname of the player who's sending the emote.
     */
    public Emote(String sender) {
        this.sender = sender;
    }

    /**
     * Gets the sender of the emote.
     * @return The sender of the emote.
     */
    public String getSender() {
        return this.sender;
    }

    /**
     * Sets the sender of the emote.
     * @param sender The sender of the emote.
     */
    public void setSender(String sender) {
        this.sender = sender;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Emote)) return false;
        Emote emote = (Emote) o;
        return Objects.equals(getSender(), emote.getSender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSender());
    }
}
