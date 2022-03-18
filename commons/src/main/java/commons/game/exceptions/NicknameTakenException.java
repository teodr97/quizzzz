package commons.game.exceptions;

public class NicknameTakenException extends Exception {

    private String message;

    public NicknameTakenException(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    public String getMessage() {
        return message;
    }
}