package client.scenes;

import client.MyFXML;
import client.MyModule;


import client.Networking.wsClient;
import javafx.fxml.FXML;



import com.google.inject.Inject;
import com.google.inject.Injector;
import jakarta.ws.rs.client.ClientBuilder;


import jakarta.ws.rs.core.Response;
import javafx.event.ActionEvent;


import javafx.fxml.Initializable;
import javafx.scene.Scene;

import javafx.scene.control.ListView;

import javafx.scene.text.Text;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.messaging.WebSocketStompClient;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import java.util.ResourceBundle;

import static com.google.inject.Guice.createInjector;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class WaitingRoom implements Initializable {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);
    private static final String SERVER = "http://localhost:8080/";


    private final MainCtrl mainCtrl;

    private Username usernameCtrl;

    private Scene overview;

    private Thread httpclientthread;
    private Thread wsclientthread;

    private WebSocketStompClient stompClient;

    private boolean dontstop;

    @FXML
    private ListView lobby;

    @FXML
    private Text players;

    @FXML
    public Text greetings;



    @Inject
    public WaitingRoom( MainCtrl mainCtrl) throws InterruptedException {
        this.mainCtrl = mainCtrl;
        this.dontstop = true;

        this.players = new Text();
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //in the waiting we room we will start the longspamming thread and the websocket thread

        //the httpclient thread
        this.httpclientthread = new Thread(new Runnable() {
            @Override
            public void run() {
                // code goes here.
                longpollUpdateLobby();

            }
        });
        httpclientthread.start();

        //the websocket client thread
        //the websocket runnable takes as paramter the controller so we can edit things inside
        // the
//        this.wsclientthread = new Thread(new WaitingRunnable(this) {
//            WaitingRoom thiswaiting= this.getController();
//
//            @Override
//            public void run() {
//
//                wsClient websocketClient = new wsClient(thiswaiting);
//                ListenableFuture<StompSession> f = websocketClient.connect();
////                StompSession stompSession = f.get();
//                // code goes here.
////                WebSocketClient webSocketClient = new StandardWebSocketClient();
////                stompClient = new WebSocketStompClient(webSocketClient);
////                stompClient.setMessageConverter(new MappingJackson2MessageConverter());
////
////                String url = "ws://localhost:8080/hello";
////                StompSessionHandler sessionHandler = new MySessionHandler();
////                ListenableFuture<StompSession> f = stompClient.connect(url, sessionHandler);
//
//
//                try{
//                    StompSession sessie = f.get();
//                    websocketClient.subscribeGreetings(sessie);
//                    websocketClient.sendHello(sessie);
//
//
//                }catch(Exception e){
//                    System.out.print("se");
//                }
//                //new Scanner(System.in).nextLine();
//
//
//            }
//        });
//        this.wsclientthread.start();

        wsClient websocketClient = new wsClient(this);
        ListenableFuture<StompSession> f = websocketClient.connect();
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
            StompSession sessie = f.get();
            websocketClient.subscribeGreetings(sessie);
            websocketClient.sendHello(sessie);


        }catch(Exception e){
            System.out.print("se");
        }
        //new Scanner(System.in).nextLine();

    }

    /**
     * If the event is executed then the scene switches to Splash.fxml
     * @param event
     * @throws IOException
     */
    public void leaveGame(ActionEvent event) throws IOException {
//        ClientBuilder.newClient(new ClientConfig()) //
//                .target(SERVER).path("/game/leave") //
//                .request(APPLICATION_JSON) //
//                .accept(APPLICATION_JSON) //
//                .post(Entity.entity(mainCtrl.getPlayer(), APPLICATION_JSON));
//
//        mainCtrl.switchToSplash();
    }

    /**
     * Keeps polling the lobby asking for the list of players
     */
    //recursive function that keeps requesting the server for new data
    //in a longpolling fashion
    public void longpollUpdateLobby(){
        //this get requests gets all the players that are connected/connecting to the server
        Response playersResponse = ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/game/getPlayers/0") //
                .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE)//
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get();



        ArrayList<String> playersstring = playersResponse.readEntity(ArrayList.class);

        players.setText("connected players: " + playersstring.toString());

        //System.out.println(playersstring.toString());
        while(playersstring.size()>=1 && dontstop){
            playersResponse = ClientBuilder.newClient(new ClientConfig()) //
                    .target(SERVER).path("/game/getPlayers/0") //
                    .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE)//
                    .request(APPLICATION_JSON) //
                    .accept(APPLICATION_JSON) //
                    .get();
            playersstring = playersResponse.readEntity(ArrayList.class);
            players.setText("connected players: " + playersstring.toString());
            //System.out.println(playersstring.toString());

        }



    }

    /**
     * start the game and switch to the mulitplayer screen
     * Also stop polling the lobby for the player list
     */
    public void startGame(){
        //stop the lobby spamming thread
        this.dontstop = false;
        this.mainCtrl.switchToMultiplayer();
    }

    /**Leave the waiting rooom
     *
     */
    public void leaveGame(){
        return;
    }

    //renders the lobby in fxml fil

    //we switched controllers so we add the leave game methode so that the fxml won't throw an error
    // for no it's just a place holder tho because we dont have acces to the player object we want to post

}
