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
import java.util.TimerTask;


@Controller
@Slf4j
@EnableScheduling
public class GreetingController {


    private ArrayList<String> questionList  = new ArrayList<>();
   public Iterator<String> questionIterator;

    @Autowired
    public SimpMessagingTemplate template;



    private String HardcodedQ = "Nuclear physicist whet?";
    private String HardcodedQ1 = "Nuclear reactors huuh?";

    //probably will be replaced wiht game model class atritbute
    public boolean gameended = false;

    public GreetingController(){questionList.add("Nuclear reactors huuh?");
        questionList.add("Nuclear physicist whet?");
        questionList.add("Tesla who?");
        questionList.add("Edison BOO");
        questionList.add("Elon to the moon?");
        questionList.add("Bitcoin green?");

        questionIterator = questionList.iterator();




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

//    /**
//     * @param message get message user sends to endpoint "/app/hello"
//     * @return send a message to the client
//     */
////    @MessageMapping("/getquestions")
////    @SendTo("/topic/questions")
////    public Message getQuestion(Message message){
////        System.out.println(message.toString());
////        return new Message(MessageType.QUESTION, "Server", HardcodedQ);
////    }

    //sending a message to "/getquestions" triggers the server to send  the questions every 10 seconds
    // so we make a timer whihc has as task to send the next question in the question iterator
    @MessageMapping("/getquestions")
    @SendTo("/topic/questions")
    public void sendQuestion(){
        Timer qtimer = new Timer();
        qtimer.scheduleAtFixedRate(new sendQuestionTask(this), 0, 10000 );






    }






}
