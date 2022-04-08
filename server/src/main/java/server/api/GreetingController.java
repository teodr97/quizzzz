package server.api;

import commons.game.Activity;
import commons.models.Message;
import commons.models.MessageType;
import commons.models.Question;
import commons.models.Emote;
import commons.models.Game;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Controller;

import server.database.ActivityRepository;
import java.util.*;


@Controller
@Slf4j
@EnableScheduling
public class GreetingController {


    public Iterator<Question> questionIterator;

   public Game game;

   private final ActivityRepository repository;
   private ArrayList<Activity> allactivies;

    private ArrayList<Activity> activitySet = new ArrayList<>();


    private ArrayList<Activity> testList = new ArrayList<>();

    private int questoincounter;


    @Autowired
    public SimpMessagingTemplate template;

    public Timer qtimer;

    //to stop the sending of question we need this field
    @Autowired
    private ScheduledAnnotationBeanPostProcessor postProcessor;

    //probably will be replaced wiht game model class atritbute
    public boolean gamestarted = false;

    @Autowired
    public GreetingController(ActivityRepository repo){
        this.repository = repo;

    }

    /**
     * For testing purposes.
     */
    public GreetingController() {
        repository = null;
    }

    /**
     * @param message get message user sends to endpoint "/app/hello"
     * @return send a message to the client
     */
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message greeting(Message message){
        System.out.println(message.toString());

        return new Message(MessageType.CONNECTED, "Server", "We see you have connected, ");
    }

    /**
     * @param message get message user sends to endpoint "/app/start" which indicates that a player has clicked
     * on the start game button in the waiting room
     * @return send a message to the client
     */
    @MessageMapping("/start")
    @SendTo("/topic/gamestate")
    public Message start(Message message){

        System.out.println(message.toString());

        //retrieve all activites from the database
        try{
            this.allactivies = (ArrayList<Activity>) repository.findAll();
            this.game = new Game();
            this.game.createQuestionList(allactivies);
            this.questionIterator = Arrays.stream(game.questions).iterator();
            qtimer = new Timer();
        }catch(Exception eX){
            System.out.println(eX.getMessage());
        }

        List<Integer> alreadyChosenIndexes = new ArrayList<>();

        

        //will be replaced  with game state functionality:
        gamestarted = true;
        return new Message(MessageType.GAME_STARTED, "Server", "Someone started the game");
    }

    /**After someone clicked the startgame button we start sending every questoin to the client every 10 seconds
     *
     */
    @Scheduled(fixedRate = 10000)
    public void sendQuestion(){
        //the game started reference will probably be a game class attribute
        if(questionIterator == null){
            return;
        }


        if(gamestarted){
            //Question current = questionIterator.next();

            //this.template.convertAndSend("/topic/questions", new Message(MessageType.QUESTION, "server", this.questionIterator.next()));
            if(template != null) this.template.convertAndSend("/topic/questions", questionIterator.next());
            System.out.println("sent a question");
        }
        if(!questionIterator.hasNext()) {
            //send game over screen and stop the scheduled sending of questions
            //TO-DO:
            //this.template.convertAndSend("/topic/greetings", new Message());
            gamestarted = false;
            Message gamestopmsg = new Message(MessageType.GAME_ENDED, "Server", "The game has ended");
            this.template.convertAndSend("/topic/gamestate", gamestopmsg);
            stopSending();
        }
    }

    /**
     * Stops sending quesitons every 10 seconds
     */
    public void stopSending(){
        postProcessor.postProcessBeforeDestruction(this, "");
    }


    /**
     * @param message Get message send to the url "/app/clickedJoker"
     * @return returns the same messgae to the clients subscribed to /topic/jokers
     */
    @MessageMapping("/clickedJoker")
    @SendTo("/topic/jokers")
    public Message handleJoker(Message message){
        System.out.println("someone clicked a joker");
        return new Message(message.getMsgType(), "Server", message.getContent());
    }

    /**
     * Get the amount of activities in the database
     * @return the amount of activities in the database
     */
    int getActivitiesSize() {
        if (allactivies == null || allactivies.isEmpty()) allactivies = (ArrayList<Activity>) repository.findAll();
        return allactivies.size();
    }

    /**
     * This function returns a set of three random questions upon request from
     * the client.
     * @return List of three random, different activities from the database
     */
    public List<Activity> retrieveRandomActivitiesSet() {
        List<Activity> activitySet = new LinkedList<>();
        List<Integer> alreadyChosenIndexes = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            int index = (int)(Math.random() * getActivitiesSize());

            while (alreadyChosenIndexes.contains(index)) index = (int)(Math.random() * getActivitiesSize());
            activitySet.add(allactivies.get(index));
            alreadyChosenIndexes.add(index);
        }
        return activitySet;
    }

    /**
     * Handler method for emotes sending.
     * @param emote the emote being sent over the server through "/app/sendEmote"
     * @return emote to be received by the clients subscribed to "/topic/jokers"
     */
    @MessageMapping("/sendEmote")
    @SendTo("/topic/emotes")
    public Emote handleEmote(Emote emote) {
        return new Emote(emote.getUsername(), emote.getReactionId());
    }
}
