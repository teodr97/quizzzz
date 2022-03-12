package commons.game.exceptions;

public class GameAlreadyExistsException extends Exception {

    private String message;

    public GameAlreadyExistsException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}