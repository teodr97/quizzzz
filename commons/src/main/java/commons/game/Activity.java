package commons.game;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Override
    public String toString() {
        return "{Activity: " + this.activity + " | Power: " + this.power + " | Id: " + this.id + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity1 = (Activity) o;
        return power == activity1.power && activity.equals(activity1.activity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activity, power);
    }
}