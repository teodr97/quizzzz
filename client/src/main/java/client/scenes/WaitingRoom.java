package client.scenes;

import client.MyFXML;
import client.MyModule;


import client.Networking.WsClient;
import commons.models.Message;
import commons.models.MessageType;

import commons.models.Player;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import javafx.fxml.FXML;



import com.google.inject.Inject;
import com.google.inject.Injector;
import jakarta.ws.rs.client.ClientBuilder;

import javafx.event.ActionEvent;




import javafx.fxml.Initializable;
import javafx.scene.Scene;

import javafx.scene.control.ListView;

import javafx.scene.text.Text;


import javafx.scene.text.TextAlignment;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.messaging.WebSocketStompClient;


import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;

import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.inject.Guice.createInjector;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class WaitingRoom implements Initializable {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);
    private static final String SERVER = "http://localhost:8080/";


    private final MultiPlayer multiplayer;
    private final MainCtrl mainCtrl;


    private static ExecutorService exec = Executors.newSingleThreadExecutor();
    private Username usernameCtrl;

    private Scene overview;

    private Thread httpclientthread;
    private Thread wsclientthread;

    private WebSocketStompClient stompClient;

    private boolean dontstop;
    public boolean gamestarted;
    private Message incomingmsg;


    @FXML
    private ListView lobby;

    @FXML
    private Text players;

    @FXML
    public Text greetings;



    @Inject
    public WaitingRoom( MainCtrl mainCtrl, MultiPlayer multiplayer) throws InterruptedException {
        this.mainCtrl = mainCtrl;
        this.dontstop = true;
        this.multiplayer = multiplayer;

        this.players = new Text();

        this.gamestarted = false;
    }

    //no real functionality yet

    /**
     * @param location url we use to acces scene
     * @param resources resources used for scene
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //in the waiting we room we will start the longspamming thread and the websocket thread



        //the websocket client thread
        //the websocket runnable takes as paramter the controller so we can edit things inside
        // the
        this.wsclientthread = new Thread(new WaitingRunnable(this) {
            WaitingRoom thiswaiting= this.getController();

            @Override
            public void run() {

                thiswaiting.mainCtrl.wsclient = new WsClient(thiswaiting);
                ListenableFuture<StompSession> f = thiswaiting.mainCtrl.wsclient.connect();
//                StompSession stompSession = f.get();
                // code goes here.
//                WebSocketClient webSocketClient = new StandardWebSocketClient();
//                stompClient = new WebSocketStompClient(webSocketClient);
//                stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//
//                String url = "ws://localhost:8080/hello";
//                StompSessionHandler sessionHandler = new MySessionHandler();
//                ListenableFuture<StompSession> f = stompClient.connect(url, sessionHandler);

                try{
                    thiswaiting.mainCtrl.sessie = f.get();
                    thiswaiting.mainCtrl.sessie.subscribe("/topic/greetings", new StompFrameHandler() {

                        public Type getPayloadType(StompHeaders stompHeaders) {
                            return Message.class;
                        }

                        public void handleFrame(StompHeaders stompHeaders, Object payload) {
                            incomingmsg = (Message) payload;
                            if(incomingmsg.getMsgType() == MessageType.CONNECTED){
                                System.out.println("You;ve joined the waiting room");
                                greetings.setText("You;ve joined the waiting room");
                            }
                        }
                    });
                    Message greetingsMsg = new Message(MessageType.CONNECT, "client", "Hello server");
                    thiswaiting.mainCtrl.sessie.send("/app/hello", greetingsMsg);
                    thiswaiting.mainCtrl.sessie.subscribe("/topic/gamestate", new StompFrameHandler() {

                        public Type getPayloadType(StompHeaders stompHeaders) {
                            return Message.class;
                        }

                        public void handleFrame(StompHeaders stompHeaders, Object payload) {
                            incomingmsg = (Message) payload;
                            if(incomingmsg.getMsgType() == MessageType.GAME_STARTED){
                                System.out.println("someone started the game so we starting");
                                startGame2();
                            }
                        }
                    });
                }catch(Exception e){
                    System.out.print(e.getMessage());
                }
                //new Scanner(System.in).nextLine();
            }
        });
        this.wsclientthread.start();
    }


    /**
     * Sends a request to start the game. Sends the player who started as the parameter.
     * Game will be set to ongoing state.
     * @param event
     * @throws IOException
     */
    public void startGame(ActionEvent event) throws IOException{
        ClientBuilder.newClient(new ClientConfig()) //
                .target(mainCtrl.SERVER).path("/game/start") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(mainCtrl.getPlayer(), APPLICATION_JSON));
        stop();
        mainCtrl.switchToMultiplayer();
    }

    /**
     * If the event is executed then the scene switches to Splash.fxml
     * @param event
     * @throws IOException
     */
    public void leaveGame(ActionEvent event) throws IOException {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/game/leave")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
               .post(Entity.entity(mainCtrl.getPlayer(), APPLICATION_JSON));

        mainCtrl.switchToSplash();
    }

    /** keep Asking the server for the player list
     * @param player
     */
    //recursive function that keeps requesting the server for new data
    //in a longpolling fashion
    public void longpollUpdateLobby(Player player){
        //this get requests gets all the players that are connected/connecting to the server
        System.out.println("Before thread.");
        exec = Executors.newSingleThreadExecutor();
        exec.submit(() -> {
            while(!Thread.interrupted()){
                System.out.println("Starting thread.");
                ArrayList<Player> playersResponse = ClientBuilder.newClient(new ClientConfig()) //
                        .target(SERVER).path("/game/getPlayers") //
                        .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE)
                        .queryParam("player", player.getNickname())//
                        .request(APPLICATION_JSON) //
                        .accept(APPLICATION_JSON) //
                        .get(new GenericType<ArrayList<Player>>() {});

                System.out.println("After response." + playersResponse);
                updatePlayerListText(playersResponse, players);
                System.out.println("Hello");
                System.out.println(playersResponse.toString());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    exec.shutdownNow();
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getState());
        });
    }




    /**
     * Updates the text displaying the list of players currently in lobby.
     * @param playersString the list of the usernames of players waiting in the lobby
     * @param playersText the text displaying the list of player usernames
     */
    private void updatePlayerListText(ArrayList<Player> playersString, Text playersText) {
        String output = "Players in lobby:\n";

        for (Player player : playersString) {
            output += "\u2022 " + player.getNickname() + "\n";
        }
        playersText.setTextAlignment(TextAlignment.LEFT);
        playersText.setText(output);
    }

    /**
     * start the game and switch to the mulitplayer screen
     * Also stop polling the lobby for the player list
     */
    public void sendstartGame(){
        //stop the lobby spamming thread
        //notify other players that we gon start the game;
        //send game started message to all other clients subscribed to "/topic/greetings"
        this.dontstop = false;
        gamestarted = true;
        Message startgamemsg = new Message(MessageType.GAME_STARTED, "", "someone started the game");

        this.mainCtrl.sessie.send("/app/start", startgamemsg);



    }

    /**
     * Starts the game and switches the screen to the multiplayer screen
     */
    public void startGame2(){
        System.out.println("move to next screen plax");
        this.mainCtrl.switchToMultiplayer();
    }



    /**Leave the waiting rooom
     *
     */
    public void leaveGame(){
        return;
    }

    /**
     * Stops thread
     */
    public void stop() {
        exec.shutdownNow();
    }

    //renders the lobby in fxml fil

    //we switched controllers so we add the leave game methode so that the fxml won't throw an error
    // for no it's just a place holder tho because we dont have acces to the player object we want to post

}
