package server.game;

import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.lang3.NotImplementedException;

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
     * @param answer The index of the correct activity.
     * @param prompt The type of question.
     */
    public Question(OptionsType[] options, int answer, String prompt) {
        this.options = options;
        this.answer = answer;
        this.prompt = prompt;
    }

    public OptionsType[] getOptions() {
        return options;
    }

    public int getAnswer() {
        return answer;
    }

    public String getPrompt() {
        return prompt;
    }

    public static Question generateQuestion() {
        throw new NotImplementedException("Creating Questions Not Implemented.");
    }
}
