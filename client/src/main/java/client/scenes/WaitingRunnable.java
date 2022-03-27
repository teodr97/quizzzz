package client.scenes;

import javafx.fxml.Initializable;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

public class WaitingRunnable implements Runnable{
    private WaitingRoom controller;
    @Inject
    public WaitingRunnable(WaitingRoom controller){
        this.controller = controller;

    }

    public void run(){

    }

    public WaitingRoom getController(){
        return this.controller;
    }



}
