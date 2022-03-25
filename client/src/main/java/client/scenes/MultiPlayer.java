package client.scenes;


import client.MySessionHandler;
import commons.models.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;


public class MultiPlayer implements Initializable {

    private Game game;
    private MainCtrl mainCtrl;

    @FXML
    private ImageView jokerHG;

    @FXML
    private ImageView joker2X;

    @FXML
    private ImageView jokerMB;

    @FXML
    private Button answerA;

    @FXML
    private Button answerB;

    @FXML
    private Button answerC;

    @FXML
    private Text prompt;

    @FXML
    private Text userpoint;
    private int pointsInt = 0;

    @FXML
    private Text questionField;

    @FXML
    private Text qNumber;
    //


    private WebSocketStompClient stompClient;

    @Inject
    public MultiPlayer(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        /*
        File j2x = new File("./client/src/main/resources/images/Joker2X.png");
        File jHg = new File("./client/src/main/resources/images/JokerHG.png");
        File jMb = new File("./client/src/main/resources/images/JokerMB.png");
        try {
            joker2X.setImage(new Image(j2x.getCanonicalPath()));
            jokerHG.setImage(new Image(jHg.getCanonicalPath()));
            jokerMB.setImage(new Image(jMb.getCanonicalPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                // code goes here.
                WebSocketClient webSocketClient = new StandardWebSocketClient();
                stompClient = new WebSocketStompClient(webSocketClient);
                stompClient.setMessageConverter(new MappingJackson2MessageConverter());

                String url = "ws://localhost:8080/hello";
                StompSessionHandler sessionHandler = new MySessionHandler();
                stompClient.connect(url, sessionHandler);

                new Scanner(System.in).nextLine(); // Don't close immediately.
//                try{
//                    StompSession sessie = f.get();
//                    subscribe(sessie);
//
//                }catch(Exception e){
//                    System.out.print("se");
//                }



            }
        }).start();




    }

    /**
     * switches to the splash screen, for the leave button
     * @param event
     * @throws IOException
     */
    public void switchToSplash(ActionEvent event) throws IOException {
        mainCtrl.switchToSplash();
    }

    public void subscribe(StompSession sessie){
        sessie.subscribe("/app/topic/Greetings", new StompFrameHandler() {

            public Type getPayloadType(StompHeaders stompHeaders) {
                return byte[].class;
            }

            public void handleFrame(StompHeaders stompHeaders, Object o) {
                System.out.println("Received greeting " + new String((byte[]) o));
            }
        });
    }
}



