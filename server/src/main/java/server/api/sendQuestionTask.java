package server.api;

import commons.models.Message;
import commons.models.MessageType;

import java.util.TimerTask;

public class sendQuestionTask extends TimerTask {
    private GreetingController controller;
    public sendQuestionTask(GreetingController controller){
        this.controller = controller;
    }

    @Override
    public void run(){

        if (controller.questionIterator.hasNext()) {
            controller.template.convertAndSend("/topic/questions", new Message(MessageType.QUESTION, "Server", controller.questionIterator.next()));
            System.out.println("Sent question");

        } else {
            controller.template.convertAndSend("/topic/questions", new Message(MessageType.QUESTION, "Server", "GAME ENDED"));
            System.out.println("game has ended");
            this.cancel();

        }




    }



}
