package server.api;


import commons.models.Message;
import commons.models.MessageType;
import commons.models.Question;
import commons.models.*;
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
    private ArrayList<String> answerList  = new ArrayList<>();
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

        answerList.add("40");
        answerList.add("30");
        answerList.add("20");

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
        //the game started reference will probably be a game class attribute

        if(gamestarted){

            //this.template.convertAndSend("/topic/questions", new Message(MessageType.QUESTION, "server", this.questionIterator.next()));
            this.template.convertAndSend("/topic/questions", new Question(this.questionIterator.next(), "Answersee"));
            System.out.println("sent a question");
        }


        //Timer qtimer = new Timer();

    }


    /**
     * @param message Get message send to the url "/app/clickedJoker"
     * @return returns the same messgae to the clients subscribed to /topic/jokers
     */
    @MessageMapping("/clickedJoker")
    @SendTo("/topic/jokers")
    public Message handleJoker(Message message){
        return new Message(message.getMsgType(), "Server", message.getContent());



    }







}
