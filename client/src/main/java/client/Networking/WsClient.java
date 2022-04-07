package client.Networking;

import client.MySessionHandler;
import client.scenes.MainCtrl;
import client.scenes.WaitingRoom;
import commons.models.Message;
import commons.models.MessageType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.inject.Inject;
import java.io.*;
import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;


public class WsClient {
    private MainCtrl mainCtrl;

//    private MultiPlayer multiplayer;
    private WaitingRoom waitingroom;

    private String nickname;

    private OutputStream os;
    private ObjectOutputStream output;
    private InputStream is;
    private ObjectInputStream input;

    private Message incomingmsg;

    private WebSocketStompClient stompClient;

    @Inject
    public WsClient(WaitingRoom waitingroom){
        this.waitingroom = waitingroom;
        //this.multiplayer =multiplayer;
    }

//
//    /** Construcotr for the clientstream class
//     * @param mainCtrl
//     * @param multiplayer
//     * @param address
//     * @param port
//     * @param nickname
//     */
//    public wsClient(MainCtrl mainCtrl, MultiPlayer multiplayer, String address, int port, String nickname){
//        this.mainCtrl = mainCtrl;
//        this.nickname = nickname;
//        this.multiplayer = multiplayer;
//
//        this.clientListener = new ClientListener(address, port);
//        this.clientListener.start();
//
//    }

    /**
     * @return Makes a stompsession from the websocket client
     */
    public ListenableFuture<StompSession> connect() {

        WebSocketClient webSocketClient = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        String url = "ws://localhost:8080/hello";
        StompSessionHandler sessionHandler = new MySessionHandler();

        return stompClient.connect(url, new ConnectionHandler());
    }

    /**
     * Connection handler
     */
    public class ConnectionHandler extends StompSessionHandlerAdapter {
        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeader) {
            System.out.println("Nice you connected");

        }
    }

    /** Subscribes stompsessino to greetings endpoint
     * @param stompSession stompsession of current ws client
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void subscribeGreetings(StompSession stompSession) throws ExecutionException, InterruptedException {

        stompSession.subscribe("/topic/greetings", new StompFrameHandler() {

            /**
             * @param stompHeaders
             * @return Sets the payload type
             */
            public Type getPayloadType(StompHeaders stompHeaders) {
                return Message.class;
            }

            /** Function that handels the payload of received message from the greetings endpoint
             * @param stompHeaders
             * @param payload paload received
             */
            public void handleFrame(StompHeaders stompHeaders, Object payload) {
                incomingmsg = (Message) payload;
                handlePayload(incomingmsg);
            }
        });
    }

    /**Auxillary handle payload message (probably gonna delete)s
     * @param incomingmsg handle a message
     */
    public void handlePayload(Message incomingmsg) {
        waitingroom.greetings.setText("Received : " + incomingmsg.getContent() + " from : " + incomingmsg.getUsername());
        if(incomingmsg.getMsgType() == MessageType.GAME_STARTED){
            System.out.println("gamestarted message type");

            //java fx thread need to be edited after the current websocket thread

            waitingroom.startGame2();
        }
        //System.out.println("Received : " + incomingmsg.getContent() + " from : " + incomingmsg.getUsername());
    }

    /**Make the client send hello to the endpoint /app/hello
     * @param stompSession session used to send this hello message
     */
    public void sendHello(StompSession stompSession) {
        Message outgoingmsg = new Message(MessageType.CONNECT, "Jordano", "Hello");
        stompSession.send("/app/hello", outgoingmsg);
    }

    /**
     * @return the incoming msg of the wsclient
     */
    public Message getIncomingmsg(){
        return this.incomingmsg;
    }


    /**
     * @param message Send mesasge in stream
     */
    private void sendMessage(Message message)
    {
        try {
            this.output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
