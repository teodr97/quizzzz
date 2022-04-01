package commons.game;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The Question entity works in a simple manner: initializing it is enough, as the constructor
 * function contains everything that is necessary (logic-wise) in order to generate a question
 * and a set of answers, of which one is correct. This object is supposed to be used only on
 * the server side, as it contains the correct answer; the clients should only receive copies
 * of the question and the answer set (without the correct answer to prevent client-side abuse)
 * in the form of a ClientQuestion object (see the subclass).
 */
public class Question {
    public String question = "<Question>";

    private List<Activity> activityList;
    private Activity correctAnswer;
    private int correctAnswerIndex;

    private int points; // Are we using this anymore?

    /**
     * Use this class for sending the data to the client side. It hides irrelevant data
     * (such as the correct answer) from the clients in order to prevent abuse from the
     * players.
     */
    public static class ClientQuestion {
        private String question;

        private List<Activity> activityList;

        /**
         * The constructor function.
         * @param question the String containing the question to be displayed
         * @param activityList the set of 3 activities that will be displayed
         */
        public ClientQuestion(String question, List<Activity> activityList) {
            this.question = question;
            this.activityList = activityList;
        }

        public String getQuestion() { return question; }

        public List<Activity> getActivityList() { return activityList; }
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

    /**
     * Constructor method.
     * @param activityList the set of 3 activities that serve as potential answers to the question
     */
    public Question(List<Activity> activityList) {
        this.activityList = activityList;
        // Shuffles the elements in the list to prevent predictable answer patterns.
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

    //SETTERS==========================================================
    public void setQuestion(String question) { this.question = question; }

    //public void setCorrectAnswer (Activity correctAnswer) { ((LinkedList<Activity>)activityList).set(correctAnswerIndex, correctAnswer); }

    //GETTERS==========================================================
    public String getQuestion() { return this.question; }

    public List<Activity> getActivityList() { return activityList; }

    public Activity getCorrectAnswer() { return this.activityList.get(correctAnswerIndex); }

    public int getCorrectAnswerIndex() { return correctAnswerIndex; }

    @Override
    public int hashCode() {
        return Objects.hash(question, activityList);
    }

    @Override
    public String toString() {
        String ret = "Question: " + question;
        ret += "\nOption: " + this.activityList.get(0).toString();
        ret += "\nOption: " + this.activityList.get(1).toString();
        ret += "\nOption: " + this.activityList.get(2).toString();
        ret += "\nAnswer: " + this.activityList.get(correctAnswerIndex).toString();
        ret += "\n}";
        return ret;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Question question1 = (Question) other;
        return activityList.equals(question1.getActivityList()) && question.equals(question1.getQuestion()) &&
                getCorrectAnswer().equals(question1.getCorrectAnswer());
    }

}
