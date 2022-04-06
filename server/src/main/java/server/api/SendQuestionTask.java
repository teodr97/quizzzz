package server.api;

import commons.models.Game;
import commons.models.Message;
import commons.models.MessageType;
import commons.models.Question;

import java.util.TimerTask;

class SendQuestionTask implements Runnable {
    private GreetingController controller;
    private String gameid;

    public SendQuestionTask(GreetingController controller, String gameid){
        this.controller = controller;
        this.gameid = gameid;

    }

    @Override
    public void run(){
//        try{
//            Thread.sleep(1000);
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//        }
        Game currentgame = controller.gamestorage.get(Integer.parseInt(gameid));

        Question current = currentgame.questionIterator.next();
        String url = "/topic/questions/"+gameid;
        System.out.println("sending questoin for game:"+currentgame.getGameID());

        controller.template.convertAndSend(url, current);




    }



}
