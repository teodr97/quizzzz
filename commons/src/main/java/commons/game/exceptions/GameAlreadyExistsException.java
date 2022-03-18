package commons.game.exceptions;

public class GameAlreadyExistsException extends Exception {

    private String message;

    public GameAlreadyExistsException(String message) {
        this.message = message;
    }

    /**
     * Gets the message of the exception.
     * @return
     */
    public String getMessage() {
        return message;
    }
}