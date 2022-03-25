package server.api;


import commons.models.Message;
import commons.models.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.stereotype.Controller;



@Controller
@Slf4j
public class GreetingController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message greeting(Message message){
        System.out.println(message.toString());
        return new Message(MessageType.CONNECTED, "Server", "We see you have connected, ");
    }



}
