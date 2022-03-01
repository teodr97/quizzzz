package commons.game;

public class Question {

    /**
     * The activity options of the question.
     */
    private Activity[] activities;

    /**
     * The index of the correct activity, should within [0, activities.size).
     */
    private int answer;

    /**
     * The prompt of the question, telling the user what the question is.
     */
    private String prompt;

    /**
     * Question Constructor.
     * @param activities The activities of the question.
     * @param answer The index of the correct activity.
     * @param prompt The type of question.
     */
    public Question(Activity[] activities, int answer, String prompt) {
        this.activities = activities;
        this.answer = answer;
        this.prompt = prompt;
    }

    public Activity[] getActivities() {
        return activities;
    }

    public int getAnswer() {
        return answer;
    }

    public String getPrompt() {
        return prompt;
    }
}
