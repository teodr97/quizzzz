package client.scenes;

import javafx.application.Platform;


import java.util.TimerTask;

public class IncreaseTimerBar extends TimerTask {

    private final MultiPlayer  multiplayer;


    /** This handels the increasing of the timer bar it is manually synched with the time on the server
     * the server sends question every 10 secoinds and the timer bar fills up every 10 seconds
     * @param multiplayer multiplayer controller so we can change things on it
     */
    public IncreaseTimerBar(MultiPlayer multiplayer){
        this.multiplayer = multiplayer;
    }

    @Override
    public void run(){
        multiplayer.progress += multiplayer.progressInc;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                multiplayer.timerBar.setProgress(multiplayer.progress);
            }
        });



    }

}
