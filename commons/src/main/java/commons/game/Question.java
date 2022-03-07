package commons.game;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private int id;
    private String question;

    /**
     * Constructor method.
     * @param question the question that should be created as a new entity
     */
    public Question(String question) { this.question = question; }

    /**
     * Empty constructor; should not be used for anything, but the compiler whines if
     * it's missing from here.
     */
    public Question() { }

    //SETTERS==========================================================
    public void setId(int id) { this.id = id; }

    public void setQuestion(String question) { this.question = question; }

    //GETTERS==========================================================
    public int getId(){
        return this.id;
    }

    public String getQuestion(){
        return this.question;
    }

    /**
     * This function should be used for debugging purposes only
     * @return String containing the question and its ID
     */
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                '}';
    }
}
