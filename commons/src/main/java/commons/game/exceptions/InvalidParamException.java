package commons.game.exceptions;

public class InvalidParamException extends Exception {

    private String message;

    public InvalidParamException(String message) {
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