package client;

import commons.models.Message;
import commons.models.MessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class MySessionHandler extends StompSessionHandlerAdapter{
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeader){
        session.subscribe("/topic/greetings", this);
        session.subscribe("/topic/greetings", this);

        Message connectmsg = new Message(MessageType.CONNECT, "Krioyo", "connecting");
        session.send("/app/hello", connectmsg);

    }
}
