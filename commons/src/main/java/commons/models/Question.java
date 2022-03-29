package commons.models;

import java.util.ArrayList;
import java.util.Objects;

public class Question {

    String question;

    String Answer;

    ArrayList<String> fakeAnswers;




    /** Constructor for the question class
     * @param question The actual question as a string
     * @param Answer The answer of the questino as a string
     * @param fakeAnswers an ArrayList of wrongAnswers as strings (2 fake answers)
     */
    public Question(String question, String Answer, ArrayList<String> fakeAnswers){
        this.question = question;
        this.Answer = Answer;
        this.fakeAnswers = fakeAnswers;

    }

    /** Getter of the question string
     * @return returns the aactual question as a string
     */
    public String getQuestion() {
        return question;
    }


    /**
     * @return the answer corresponding to the question as a string
     */
    public String getAnswer() {
        return Answer;
    }


    /**
     * @return the fake answers as an arraylist of strings to the questions
     */
    public ArrayList<String> getFakeAnswers() {
        return fakeAnswers;
    }


    /**
     * @param question Set the question of the class
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @param answer set the answer of the class
     */
    public void setAnswer(String answer) {
        Answer = answer;
    }

    /**
     * @param fakeAnswers set the fake answers of the class
     */
    public void setFakeAnswers(ArrayList<String> fakeAnswers) {
        this.fakeAnswers = fakeAnswers;
    }


    /**
     * @param o Other object for which we want to check equality
     * @return true if this' attributes are is equal to O and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return Objects.equals(question, question1.question) && Objects.equals(Answer, question1.Answer) && Objects.equals(fakeAnswers, question1.fakeAnswers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, Answer, fakeAnswers);
    }


    /**
     * @return The question and all it's attributes in human readable string format
     */
    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", Answer='" + Answer + '\'' +
                ", fakeAnswers=" + fakeAnswers +
                '}';
    }
}
