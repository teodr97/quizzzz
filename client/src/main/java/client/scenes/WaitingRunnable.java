package client.scenes;

import javafx.fxml.Initializable;
import org.springframework.stereotype.Controller;

public class WaitingRunnable implements Runnable{
    private WaitingRoom controller;
    public WaitingRunnable(WaitingRoom controller){
        this.controller = controller;

    }

    public void run(){

    }

    public WaitingRoom getController(){
        return this.controller;
    }



}
