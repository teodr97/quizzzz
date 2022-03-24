package client;

import commons.models.Message;
import commons.models.MessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class MySessionHandler extends StompSessionHandlerAdapter{
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeader){
        //session.subscribe("/topic/Game", this);
        System.out.println("Nice you connected");
//        Message connectmsg = new Message(MessageType.CONNECT, "Krioyo", "connecting");
//        Message connectmsg1 = new Message(MessageType.START_GAME, "Krioyo", "krioyo started the game");
//        //session.send("/app/hello", connectmsg);
//        session.send("/app/topic/Greetings", connectmsg1.getContent());



    }






}
