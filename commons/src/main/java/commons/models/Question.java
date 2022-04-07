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

    public String question;
    private String answer;
    private int correctAnswerIndex;
    private ArrayList<Activity> activityList = new ArrayList<>();
    private int questionNo;

    public Question(){

    }


    /**
     * This function compares a given answer against the
     * correct one and decides if it's correct or not.
     * @param question The question that's currently on-screen
     * @param answerIndex The index of the player's answer within the option list contained
     *                    by the question
     * @return True or false, depending on whether the answer is correct or not
     */
    public static boolean hasCorrectAnswer(Question question, int answerIndex) {
        return answerIndex == question.correctAnswerIndex;
    }


    /** Constructtor of the message object
     * is extends from class message and we give is message type question
     *
     * @param
     */
    public Question(ArrayList<Activity> allactivites){
        super(MessageType.QUESTION, "server", "");

        List<Integer> alreadyChosenIndexes = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            int index = (int)(Math.random() * allactivites.size());

            while (alreadyChosenIndexes.contains(index)) index = (int)(Math.random() * allactivites.size());
            this.activityList.add(allactivites.get(index));
            alreadyChosenIndexes.add(index);
        }


        Collections.shuffle(activityList);

        // Generate a new random integer to determine the type of question that will be used.
        // Not a good solution for a greater amount of questions, they should be stored in a
        // database instead.
        int randomFactor = Utils.generateRandomIntSmallerThan(4);
        switch (randomFactor) {
            case 1:
                this.question = "Which activity uses the most amount of power?";
                this.correctAnswerIndex = Utils.retrieveActivityMostEnergy(activityList);
                break;
            case 2:
                this.question = "Which activity uses the least amount of power?";
                this.correctAnswerIndex = Utils.retrieveActivityLeastEnergy(activityList);
                break;
            default:
                // Generate a random integer from 0 to 2 for getting an index for the correct answer
                int correctActivityIndex = Utils.generateRandomIntSmallerThan(3);
                correctAnswerIndex = correctActivityIndex;
                // Retrieve a random activity that will serve as the correct answer using indexes 0-3
                this.question = "How much power does the following activity use:\n\"" + activityList.get(correctActivityIndex).getTitle() + "\"";
                this.activityList = Utils.replaceActivitiesWithPowerDraws(activityList, correctActivityIndex);
                break;
        }


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

    public String getQuestion() { return this.question; }

    public List<Activity> getActivityList() { return activityList; }


    /**
     * @return returns the index of the correct answer
     */
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    /**
     * @return returns the correct answer
     */
    public Activity getCorrectAnswer() { return this.activityList.get(correctAnswerIndex); }


    /** Set the question for this question
     * @param question question to be set as a string
     */
    public void setQuestion(String question){
        this.question = question;
    }


    /**
     * @return gets the question number
     */
    public int getQuestionNo() {
        return questionNo;
    }

    /**
     * @param questionNo sets the queston number
     */
    public void setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
    }

    /**
     * @param answer Sets the answers to this question
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }


    /**
     * @return returns the content of the messge as a string
     */
    public String getContent() {return super.getContent();}


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
        return (super.toString()+this.question);

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
        return Objects.equals(question, question.question)
                && Objects.equals(activityList, question.activityList);
    }

}