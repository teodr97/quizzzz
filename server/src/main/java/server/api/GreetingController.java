package server.api;

import commons.models.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import static commons.models.MessageType.CONNECTED;
import static commons.models.MessageType.START_GAME;

@Controller
@Slf4j
public class GreetingController {
    @MessageMapping("/hello")
    @SendTo("topic/Greetings")
    public Message greeting(Message message){
        System.out.println("hello user");
        return new Message(CONNECTED, "server", "Hello, "+ message.getUsername() +"!");
    }

    @MessageMapping("/stargtgame")
    @SendTo("topic/Game")
    public Message gamestarted(Message message){
        System.out.println("user started game");
        return new Message(START_GAME, "server", "game hsa been started");
    }


}
