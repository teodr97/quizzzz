package client.Networking;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutionException;

import client.MySessionHandler;
import client.scenes.MainCtrl;
import client.scenes.MultiPlayer;
import commons.models.Message;
import commons.models.MessageType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;


public class wsClient {
    private MainCtrl mainCtrl;

    private MultiPlayer multiplayer;


    private String nickname;

    private OutputStream os;
    private ObjectOutputStream output;
    private InputStream is;
    private ObjectInputStream input;

    private Message incomingmsg;

    private WebSocketStompClient stompClient;
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

    public ListenableFuture<StompSession> connect() {

        WebSocketClient webSocketClient = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        String url = "ws://localhost:8080/hello";
        StompSessionHandler sessionHandler = new MySessionHandler();

        return stompClient.connect(url, new connectionHandler());
    }

    public class connectionHandler extends StompSessionHandlerAdapter {
        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeader) {
            System.out.println("Nice you connected");

        }
    }

    public Message subscribeGreetings(StompSession stompSession) throws ExecutionException, InterruptedException {

        stompSession.subscribe("/topic/greetings", new StompFrameHandler() {

            public Type getPayloadType(StompHeaders stompHeaders) {
                return Message.class;
            }

            public void handleFrame(StompHeaders stompHeaders, Object payload) {
                incomingmsg = (Message) payload;
                //System.out.println("Received : " + incomingmsg.getContent() + " from : " + incomingmsg.getUsername());
            }
        });

        return this.incomingmsg;
    }

    public void sendHello(StompSession stompSession) {
        Message outgoingmsg = new Message(MessageType.CONNECT, "Jordano", "Hello");
        stompSession.send("/app/hello", outgoingmsg);
    }

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
