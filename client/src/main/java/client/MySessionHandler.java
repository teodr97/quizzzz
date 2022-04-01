package client;

import client.scenes.*;

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

    private WaitingRoom controller;
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

    /**
     * @param session Current session
     * @param command command set if any
     * @param headers headers received from this subscription
     * @param payload payload received from the subscription
     * @param exception exception received if any
     */
    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception", exception);
    }

    /** Set's the type of what the users wants to receive from the subscription
     * @param headers headrs of the message from the subscription
     * @return returns the type
     */
    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    /** This is where the message from the server gets handled
     * @param headers headers from the subscription message
     * @param payload payload from the subscription message
     */
    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Message msg = (Message) payload;
        System.out.println("Received : " + msg.getContent() + " from : " + msg.getUsername());
    }







}
