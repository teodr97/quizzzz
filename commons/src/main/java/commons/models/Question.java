package commons.models;

import commons.game.Activity;
import commons.game.Utils;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

//question class extends from the mesasge class
//always has as type MessageType.Question
//content is now called question
// we retrieve content by calling this.getQuestion.
public class Question extends Message{


    private String question;
    private String answer;
    private ArrayList<String> fakeAnswers;
    private ArrayList<String> shuffledAnswers;

    private List<Activity> activityList;

    private Activity correctAnswer;



    public Question(){

    }

    public Question(List<Activity> activityList){
        this.activityList = activityList;


        // Generate a new random integer to determine the type of question that will be used.
        // Not a good solution for a greater amount of questions, they should be stored in a
        // database instead.
        int randomFactor = Utils.generateRandomIntSmallerThan(4);
        switch (randomFactor) {
            case 1:
                
                this.question = "Which activity uses the most amount of power?";
                this.correctAnswer = Utils.retrieveActivityMostEnergy(activityList);
                break;
            case 2:
                this.question = "Which activity uses the least amount of power?";
                this.correctAnswer = Utils.retrieveActivityLeastEnergy(activityList);
                break;
            default:
                // Generate a random integer from 0 to 2 for getting an index for the correct answer
                int correctAnswerIndex = Utils.generateRandomIntSmallerThan(3);
                // Retrieve a random activity that will serve as the correct answer using indexes 0-3
                this.correctAnswer = activityList.get(correctAnswerIndex);
                this.question = "How much power does the following activity use:\n\"" + correctAnswer.getTitle() + "\"";
                this.activityList = Utils.replaceActivitiesWithPowerDraws(activityList, correctAnswerIndex);
                break;
        }


        super(MessageType.QUESTION, "server", content);
        this.answer = answer;
        act = new Activity("/image", "act", 232, "src");
        act2 = new ActivityEntry(Integer.toString(act.getAutoId()),
                act.getTitle(),
                act.getSource(),
                act.getImage_path(),
                Long.toString(act.getConsumption_in_wh()));
    }

    /** Constructtor of the message object
     * is extends from class message and we give is message type question
     *
     * @param content content of the message object
     */
    public Question(String content, String answer, ArrayList<String> fakeAnswers){
        super(MessageType.QUESTION, "server", content);
        this.answer = answer;
        this.fakeAnswers = fakeAnswers;

        //shuffle the answers when the question object is initiliazed
        shuffledAnswers = new ArrayList<>();
        shuffledAnswers.add(this.answer);
        for(String fake: fakeAnswers){
            shuffledAnswers.add(fake);
        }
        Collections.shuffle(shuffledAnswers);
    }



    /**
     * @return Getter for the message type returns it as a Messagetype object
     */
    public MessageType getMsgType() {return super.getMsgType();}


    /**
     * @param msgType Setter for the msg type
     */
    public void setMsgType(MessageType msgType) {super.setMsgType(msgType);}


    /**
     * @return returns the username of the person that sends the message as a string
     */
    public String getUsername() {return super.getUsername();}


    /**
     * @return the answer of this question as a string
     */
    public String getAnswer(){
        return this.answer;
    }

    /**
     * @return returns the arraylist with fake answers usually two fake answers
     */
    public ArrayList<String> getFakeAnswers(){
        return this.fakeAnswers;
    }

    /** Set the question for this question
     * @param question question to be set as a string
     */
    public void setQuestion(String question){
        super.setContent(question);
    }


    /**
     * @param answer Sets the answers to this question
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @param fakeAnswers Sets the array of fake asnwers to this question
     */
    public void setFakeAnswers(ArrayList<String> fakeAnswers) {
        this.fakeAnswers = fakeAnswers;
    }

    /**
     * @return returns the content of the messge as a string
     */
    public String getQuestion() {return super.getContent();}


    /**
     * @param content Sets the content of the messgage
     */
    public void setContent(String content) {super.setContent(content);}


    /**
     * @return returns the message object as a readable string
     * the to string methode causes problems with the sending of the object through websockets
     * if the string is to complicated I don't know why this is but that's why
     * we keep it so small and simple
     */
    public String toString()
    {
        return (super.toString());

    }

    /**
     * @return returns the a string of the questoin object in human readable form
     */
    public String realString();
    {
        String ret = "Question: " + question;
        ret += "\nOption: " + this.activityList.get(0).toString();
        ret += "\nOption: " + this.activityList.get(1).toString();
        ret += "\nOption: " + this.activityList.get(2).toString();
        ret += "\nAnswer: " + this.correctAnswer.toString();
        ret += "\n}";
        return ret;
    }

    /**
     * @param other object to check equality with
     * @return true if attributes equal false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Question question = (Question) other;
        return Objects.equals(content, question.content) && Objects.equals(answer, question.answer)
                && Objects.equals(fakeAnswers, question.fakeAnswers);
    }

    /**
     * @return The array of shuffled answers
     */
    public ArrayList<String> getShuffledAnswers(){

        return this.shuffledAnswers;

    }


}
