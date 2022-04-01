package commons.game.exceptions;

public class ImageUploadException extends RuntimeException{
    private String message;

    public ImageUploadException(String message) {
        super(message);
    }
    public ImageUploadException(String message, Throwable cause){ super(message,cause);}

    /**
     * Gets the message of the exception.
     * @return
     */
    public String getMessage() {
        return message;
    }
}
