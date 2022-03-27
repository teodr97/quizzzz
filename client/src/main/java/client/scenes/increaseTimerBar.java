package client.scenes;

import java.util.TimerTask;

public class increaseTimerBar extends TimerTask {

    private MultiPlayer multiplayer;
    private static final double EPSILON = 0.00001;
    private double progress;


    public increaseTimerBar(MultiPlayer multiplayer){
        this.multiplayer = multiplayer;
    }

    @Override
    public void run(){
        if((multiplayer.timerBar.getProgress() + EPSILON > 1 && multiplayer.timerBar.getProgress() - EPSILON <1)){
            //update the current round + 1
            //game.setCurRound(game.getCurRound()+1);
            if(multiplayer.prompt != null){
                if(multiplayer.prompt.getText().equals("")){
                    multiplayer.prompt.setText("Timer over");
                }
            }
            progress = 0;

            //when timer ends and game hasn't ended we want to display the next question
            //disableAnswers();
        }
        progress += multiplayer.progressInc;
        multiplayer.timerBar.setProgress(progress);
        if(multiplayer.gamended){
            this.cancel();

        }

    }

}
