package server.api;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private int id;
    private String question;

    public Question(String question){
        this.question = question;
    }

    public Question() {

    }

    public void setId(int id){
        this.id = id;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public int getId(){
        return this.id;
    }

    public String getQuestion(){
        return this.question;
    }

}
