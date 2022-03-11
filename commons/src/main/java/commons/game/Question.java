package commons.game;


import javax.swing.text.html.Option;

public class Question<OptionsType> {

    /**
     * The activity options of the question.
     */
    private OptionsType[] options;

    /**
     * The index of the correct activity, should within [0, activities.size).
     */
    private OptionsType answer;

    /**
     * The prompt of the question, telling the user what the question is.
     */
    private String prompt;

    /**
     * Question Constructor.
     * @param options The activities of the question.
     * @param answer The correct activity
     * @param prompt The type of question.
     */
    public Question(OptionsType[] options, OptionsType answer, String prompt) {
        this.options = options;
        this.answer = answer;
        this.prompt = prompt;
    }

    /** TEMPORARY METHOD (REPLACE WITH DATABASE QUERIES)
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

        return new Question<>(activityList, answer,"What uses more energy?");
    }

    public OptionsType[] getOptions() { return options; }

    public OptionsType getAnswer() {
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
