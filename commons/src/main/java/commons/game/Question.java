package commons.game;

import java.util.Arrays;
import java.util.Objects;

public class Question<OptionsType> {

    /**
     * The activity options of the question.
     */
    private OptionsType[] options;

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
     * @param options The activities of the question.
     * @param answer The correct activity
     * @param prompt The type of question.
     */
    public Question(OptionsType[] options, int answer, String prompt) {

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
        Activity a = new Activity("Running a mile",1);
        Activity b = new Activity("Swimming a mile", 1);
        Activity c = new Activity("Biking a mile",1);
        Activity[] activityList = new Activity[]{a,b,c};
        //NEEDS TO BE CHANGED TO A GET RIGHT ANSWER METHOD
        int answer = 2;

        return new Question<>(activityList, answer,"What uses more energy?");
    }

    public static Question generateQuestion() {
        return null;
    }

    public OptionsType[] getOptions() { return options; }

    public int getAnswer() {
        return answer;
    }

    public String getPrompt() {
        return prompt;
    }

    @Override
    public String toString() {
        return prompt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question<?> question = (Question<?>) o;
        return Arrays.equals(options, question.options) && Objects.equals(answer, question.answer) && Objects.equals(prompt, question.prompt);
    }

}
