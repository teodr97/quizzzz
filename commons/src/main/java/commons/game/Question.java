package commons.game;

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
    public String question;

    private List<Activity> activityList;
    private Activity correctAnswer;

    private int points; // Are we using this anymore?

    /**
     * Use this class for sending the data to the client side. It hides irrelevant data
     * (such as the correct answer) from the clients in order to prevent abuse from the
     * players.
     */
    public class ClientQuestion {
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
     * @param answer The player's answer
     * @return True or false, depending on whether the answer is correct or not
     */
    public static boolean hasCorrectAnswer(Question question, Activity answer) {
        return answer.getActivity().equals(question.getCorrectAnswer().getActivity());
    }

    /**
     * Constructor method.
     * @param activityList the set of 3 activities that serve as potential answers to the question
     */
    public Question(List<Activity> activityList) {
        this.activityList = activityList;

        // Generate a new random integer to determine the type of question that will be used.
        // Not a good solution for a greater amount of questions, they should be stored in a
        // database instead.
        int randomFactor = (int) (Math.random() * 4);
        switch (randomFactor) {
            case 1:
                this.question = "Which activity uses the most amount of power?";
                int maxPower = activityList.get(0).getPower();
                this.correctAnswer = activityList.get(0);

                if (maxPower < activityList.get(1).getPower()) {
                    this.correctAnswer = activityList.get(1);
                    maxPower = this.correctAnswer.getPower();
                }
                if (maxPower < activityList.get(2).getPower()) this.correctAnswer = activityList.get(2);
                break;
            case 2:
                this.question = "Which activity uses the least amount of power?";
                int minPower = activityList.get(0).getPower();
                this.correctAnswer = activityList.get(0);

                if (minPower > activityList.get(1).getPower()) {
                    this.correctAnswer = activityList.get(1);
                    minPower = this.correctAnswer.getPower();
                }
                if (minPower > activityList.get(2).getPower()) this.correctAnswer = activityList.get(2);
                break;
            default:
                // Generate a random integer from 0 to 3 for getting an index for the correct answer
                int correctAnswerIndex = (int) (Math.random() * 4);
                // Retrieve a random activity that will serve as the correct answer using indexes 0-3
                this.correctAnswer = activityList.get(correctAnswerIndex);
                this.question = "How much power does " + correctAnswer.getActivity() + "require?";
                // We replace every string containing the activity by the power it draws,
                // as that is what we need to show for the answers the player will choose from.
                for (int i = 0; i < 3; i++) {
                    this.activityList.get(i).setActivity(Integer.toString(this.activityList.get(i).getPower()));
                    // We change the power amounts used within other answers to amounts that
                    // are different to the amount in the correct answer.
                    if (i != correctAnswerIndex) this.activityList.get(i).setActivity(Integer.toString(
                            this.activityList.get(correctAnswerIndex).getPower() + (int) (Math.random() * 100)
                    ));
                }
                break;
        }
    }

    //SETTERS==========================================================
    public void setQuestion(String question) { this.question = question; }

    //GETTERS==========================================================
    public String getQuestion(){
        return this.question;
    }

    public Activity getCorrectAnswer() { return this.correctAnswer; }

    /**
     * This function should be used for debugging purposes only
     *
     * @return String containing the question and its ID
     */
    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", activityList=" + activityList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return Objects.equals(question, question1.question) && Objects.equals(activityList, question1.activityList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, activityList);
    }
}
