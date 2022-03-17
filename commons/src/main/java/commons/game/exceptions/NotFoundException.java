package commons.game.exceptions;

public class NotFoundException extends Exception{
    public String message;

    public NotFoundException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}