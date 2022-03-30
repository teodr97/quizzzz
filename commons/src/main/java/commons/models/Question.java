package commons.models;

import java.util.ArrayList;
import java.util.Objects;

//question class extends from the mesasge class
//always has as type MessageType.Question
//content is now called question
// we retrieve content by calling this.getQuestion.
public class Question extends Message{


    private String content;
    private String answer;
    private ArrayList<String> fakeAnswers;




    public Question(){

    }

    public Question(String content, String answer){
        super(MessageType.QUESTION, "server", content);
        this.answer = answer;
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
    }

//    /** Constructtor of the message object
//     * @param type message type as type type
//     * @param username username of the person sending message
//     * @param content content of the message object
//     */
//    public Question(MessageType type, String username, String content)
//    {
//        this.msgType = type;
//
//        this.username = username;
//        this.content = content;
//    }


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
     */
    public String toString()
    {
        return super.toString();
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

    public ArrayList<String> getShuffledAnswers(){
        ArrayList<String> newarray = new ArrayList<>();
        newarray.add(this.answer);
        for(String fake: fakeAnswers){
            newarray.add(fake);
        }
    }


}
