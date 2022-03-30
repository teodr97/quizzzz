package server.api;


import commons.game.Activity;
import commons.models.Message;
import commons.models.MessageType;
import commons.models.Question;




import commons.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import server.database.ActivityRepository;


import java.util.*;


@Controller
@Slf4j
@EnableScheduling
public class GreetingController {



    private ArrayList<Question> questionList = new ArrayList<>();
    private ArrayList<String> fakeanswerList  = new ArrayList<>();

    private ArrayList<commons.game.Question> questionList2 = new ArrayList<>();
    public Iterator<commons.game.Question> questionIterator;

   public Iterator<Question> questionIterator2;

   public Game game;

   private final ActivityRepository repository;
   private List<Activity> activityList;




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
        this.activityList = (List<Activity>) repository.findAll();

        this.game = new Game();

        //retrieves the questions
        //game.createQuestionList(retrieveRandomActivitiesSet());


        fakeanswerList.add("40");
        fakeanswerList.add("30");


        questionList.add(new Question("Nuclear reactors huuh?", "20", fakeanswerList));
        questionList.add(new Question("Nuclear physicist whet?", "20", fakeanswerList));
        questionList.add(new Question("Tesla who?", "20", fakeanswerList));
        questionList.add(new Question("Edison BOO", "20", fakeanswerList));
        questionList.add(new Question("Elon to the moon?", "20", fakeanswerList));
        questionList.add(new Question("Bitcoin green?", "20", fakeanswerList));




        //questionIterator = questionList.iterator();
        qtimer = new Timer();




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

        if(gamestarted){

            //this.template.convertAndSend("/topic/questions", new Message(MessageType.QUESTION, "server", this.questionIterator.next()));
            this.template.convertAndSend("/topic/questions", questionIterator.next());
            System.out.println("sent a question");
        }
        if(!questionIterator.hasNext()){
            //send game over screen and stop the scheduled sending of questions
            //TO-DO:
            //this.template.convertAndSend("/topic/greetings", new Message());
            stopSending();
        }


        //Timer qtimer = new Timer();

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
        if (activityList == null || activityList.isEmpty()) activityList = (List<Activity>) repository.findAll();
        return activityList.size();
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
            activitySet.add(activityList.get(index));
            alreadyChosenIndexes.add(index);
        }
        return activitySet;
    }











}
