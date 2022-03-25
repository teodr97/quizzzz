package client;

import commons.models.Message;
import commons.models.MessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class MySessionHandler extends StompSessionHandlerAdapter{

    private Logger logger = LogManager.getLogger(MySessionHandler.class);
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeader){
        //session.subscribe("/topic/Game", this);
        System.out.println("Nice you connected");
        Message connectmsg = new Message(MessageType.CONNECTED, "Jordano", "Connected");
        logger.info("New session established : " + session.getSessionId());
        session.subscribe("/topic/greetings", this);
        logger.info("Subscribed to /topic/messages");
        session.send("/app/hello", connectmsg);
        logger.info("Message sent to websocket server");
//        Message connectmsg = new Message(MessageType.CONNECT, "Krioyo", "connecting");
//        Message connectmsg1 = new Message(MessageType.START_GAME, "Krioyo", "krioyo started the game");
//        //session.send("/app/hello", connectmsg);
//        session.send("/app/topic/Greetings", connectmsg1.getContent());



    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Message msg = (Message) payload;
        System.out.println("Received : " + msg.getContent() + " from : " + msg.getUsername());
    }







}
