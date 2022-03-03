package server.database;

import org.apache.catalina.realm.JAASCallbackHandler;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Activity {
    @Id
    @GeneratedValue
    private int id; // PRIMARY KEY
    private String activity;
    private int power; // The energy this activity draws

    /**
     * Constructor method.
     * @param activity the activity that should be associated to the new entity
     * @param power the power that this activity draws
     */
    public Activity(String activity, int power) { this.activity = activity; this.power = power; }

    /**
     * Empty constructor; should not be used for anything, but the compiler whines if
     * it's missing from here.
     */
    public Activity() { }

    //SETTERS==========================================================
    public void setId(int id) { this.id = id; }

    public void setActivity(String activity) { this.activity = activity; }

    public void setPower (int power) { this.power = power; }

    //GETTERS==========================================================
    public int getId() { return this.id; }

    public String getActivity() { return this.activity; }

    public int getPower() { return this.power; }

    /**
     * This function should be used for debugging purposes only
     * @return String containing the question and its ID
     */
    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", activity='" + activity + '\'' +
                ", power=" + power +
                '}';
    }
}
