package commons.game.exceptions;

public class InvalidGameException extends Exception{
    public String message;

    public InvalidGameException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}