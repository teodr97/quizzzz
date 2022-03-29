package client.scenes;

import javafx.application.Platform;

import javax.inject.Inject;
import java.util.TimerTask;

public class increaseTimerBar extends TimerTask {

    private final MultiPlayer  multiplayer;
    // if the epsilon is too small there is a chance the thread won't stop at progress  = 1;
    // and this means we will be updating the progress in two seperate threas so the prgress will double in speed
    //keep this kinda big
    private static final double EPSILON = 0.002;



    public increaseTimerBar(MultiPlayer multiplayer){
        this.multiplayer = multiplayer;
    }

    @Override
    public void run(){

        if((multiplayer.timerBar.getProgress() + EPSILON > 1 && multiplayer.timerBar.getProgress() - EPSILON <1)){
            //update the current round + 1
            //game.setCurRound(game.getCurRound()+1);

            this.cancel();
            return;


            //when timer ends and game hasn't ended we want to display the next question
            //multiplayer.disableAnswers();

        }
        System.out.println(multiplayer.progress);

        multiplayer.progress += multiplayer.progressInc;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                multiplayer.timerBar.setProgress(multiplayer.progress);
            }
        });
        multiplayer.timerBar.setProgress(multiplayer.progress);


    }

}
