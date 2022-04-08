package client.scenes;

import javafx.application.Platform;


import java.util.TimerTask;

public class IncreaseTimerBar extends TimerTask {

    private final MultiPlayer  multiplayer;
    private static final double EPSILON = 0.001;


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
                if((multiplayer.progress  + EPSILON > 1 && multiplayer.progress - EPSILON <1)){
                    //update the current round + 1
                    System.out.println("timer bar is full");
                    multiplayer.answerA.setDisable(true);
                    multiplayer.answerB.setDisable(true);
                    multiplayer.answerC.setDisable(true);
                    //when timer ends and game hasn't ended we want to display the next question
                    //multiplayer.disableAnswers();
                }
                multiplayer.timerBar.setProgress(multiplayer.progress);

            }
        });



    }

}
