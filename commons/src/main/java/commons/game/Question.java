package commons.game;


public class Question {

    /**
     * The activity options of the question.
     */
    private Activity[] activities;

    /**
     * The index of the correct activity, should within [0, activities.size).
     */
    private Activity answer;

    /**
     * The prompt of the question, telling the user what the question is.
     */
    private String prompt;

    /**
     * Question Constructor.
     * @param activities The activities of the question.
     * @param answer The correct activity
     * @param prompt The type of question.
     */
    public Question(Activity[] activities, Activity answer, String prompt) {
        this.activities = activities;
        this.answer = answer;
        this.prompt = prompt;
    }

    /** TEMPORARY METHOD (REPLACE WITH DATABASE QUERY)
     * creates a question instance for the game
     * @return a question containing a question prompt and answers
     */
    public static Question createQuestion(){

        //TEMPORARY: this whole part needs to be replaced with a database query
        Activity a = new Activity("Running a mile",1,"");
        Activity b = new Activity("Swimming a mile", 1, "");
        Activity c = new Activity("Biking a mile",1,"");
        Activity[] activityList = new Activity[]{a,b,c};
        //NEEDS TO BE CHANGED TO A GET RIGHT ANSWER METHOD
        Activity answer = b;

        return new Question(activityList, answer,"What uses more energy?");
    }

    public Activity[] getActivities() {
        return activities;
    }

    public Activity getAnswer() {
        return answer;
    }

    public String getPrompt() {
        return prompt;
    }

    @Override
    public String toString() {
        return prompt;
    }
}
