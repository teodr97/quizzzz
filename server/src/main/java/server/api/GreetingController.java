package server.api;


import commons.models.Message;
import commons.models.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;


@Controller
@Slf4j
@EnableScheduling
public class GreetingController {


    private ArrayList<String> questionList  = new ArrayList<>();
   public Iterator<String> questionIterator;

    @Autowired
    public SimpMessagingTemplate template;

    public Timer qtimer;



    private String HardcodedQ = "Nuclear physicist whet?";
    private String HardcodedQ1 = "Nuclear reactors huuh?";

    //probably will be replaced wiht game model class atritbute
    public boolean gamestarted = false;

    public GreetingController(){questionList.add("Nuclear reactors huuh?");
        questionList.add("Nuclear physicist whet?");
        questionList.add("Tesla who?");
        questionList.add("Edison BOO");
        questionList.add("Elon to the moon?");
        questionList.add("Bitcoin green?");

        questionIterator = questionList.iterator();
        qtimer = new Timer();




    }





    /**
     * @param message get message user sends to endpoint "/app/hello"
     * @return send a message to the client
     */
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message greeting(Message message){
        System.out.println(message.toString());

        return new Message(MessageType.CONNECTED, "Server", "We see you have connected, ");

    }

    /**
     * @param message get message user sends to endpoint "/app/start" which indicates that a player has clicked
     * on the start game button in the waiting room
     * @return send a message to the client
     */
    @MessageMapping("/start")
    @SendTo("/topic/gamestate")
    public Message start(Message message){
        System.out.println(message.toString());
        gamestarted = true;
        return new Message(MessageType.GAME_STARTED, "Server", "Someone started the game");

    }


    /**After someone clicked the startgame button we start sending every questoin to the client every 10 seconds
     *
     */
    @Scheduled(fixedRate = 10000)
    public void sendQuestion(){
        if(gamestarted){
            this.template.convertAndSend("/topic/questions", new Message(MessageType.QUESTION, "Server", this.questionIterator.next()));
        }


        //Timer qtimer = new Timer();

    }
    @MessageMapping("/clickedJoker")
    @SendTo("/topic/jokers")
    public Message handleJoker(Message message){
        return new Message(message.getMsgType(), "Server", message.getContent());



    }







}
