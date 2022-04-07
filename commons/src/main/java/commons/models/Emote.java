package commons.models;

public class Emote extends Message {

    private String username;

    private MessageType reactionId;

    /**
     * Empty constructor. Needed to ensure client-server communication.
     * Only used by Spring Boot.
     */
    public Emote() {}

    /**
     * Constructor function.
     * @param username The username of the player that has sent the reaction
     * @param reactionId The type of reaction the player has sent
     */
    public Emote(String username, MessageType reactionId) {
        this.username = username;
        this.reactionId = reactionId;

        // Check if the reaction/emote that we're just instantiating has a valid reaction identifier.
        assert reactionId.name().contains("REACTION") : "The reaction instantiated does not contain a valid reaction id!";
    }

    /**
     * Getter method for the username.
     * @return The username of the player who has sent the reaction
     */
    public String getUsername () { return username; }

    /**
     * Getter method for the reaction type, in the form of an identifier.
     * @return The reaction id sent by the player
     */
    public MessageType getReactionId() { return reactionId; }
}
