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
    private int autoId; // PRIMARY KEY
    private String image_path;
    private String title;
    private int consumption_in_wh;// The energy this activity draws
    private String source;

    /**
     * Constructor method
     *
     * @param image_path
     * @param title
     * @param consumption_in_wh
     * @param source
     */
    public Activity(String image_path, String title, int consumption_in_wh, String source) {
        this.image_path = image_path;
        this.title = title;
        this.consumption_in_wh = consumption_in_wh;
        this.source = source;
    }

    /**
     * Empty constructor; important for adding into database
     */
    public Activity() {
    }

    //SETTERS==========================================================

    /**
     * Sets the autoId
     * @param autoId
     */
    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    /**
     * Sets the image path
     * @param image_path
     */
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    /**
     * Sets the title of the activity
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the consumption_in_wh
     * @param consumption_in_wh
     */
    public void setConsumption_in_wh(int consumption_in_wh) {
        this.consumption_in_wh = consumption_in_wh;
    }

    /**
     * Sets the source of the activity
     * @param source
     */
    public void setSource(String source) {
        this.source = source;
    }


    //GETTERS==========================================================


    /**
     * gets the autoId
     * @return int autoId
     */
    public int getAutoId() {
        return autoId;
    }

    /**
     * gets the image_path
     * @return String image_path
     */
    public String getImage_path() {
        return image_path;
    }

    /**
     * gets the title
     * @return String title
     */
    public String getTitle() {
        return title;
    }

    /**
     * gets the consumption_in_wh
     * @return int consumption_in_wh
     */
    public int getConsumption_in_wh() {
        return consumption_in_wh;
    }

    /**
     * gets the source
     * @return String source
     */
    public String getSource() {
        return source;
    }

    /**
     * Tests if an activity is equal to given object
     * @param o: any object
     * @return Boolean value depending on if o == activity or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return consumption_in_wh == activity.consumption_in_wh && Objects.equals(image_path, activity.image_path) && Objects.equals(title, activity.title) && Objects.equals(source, activity.source);
    }

    /**
     * returns the hashcode of the activity
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(image_path, title, consumption_in_wh, source);
    }

    /**
     * Prints activity as a string, for debugging purposes
     * @return
     */
    @Override
    public String toString() {
        return "Activity{" +
                "autoId=" + autoId +
                ", image_path='" + image_path + '\'' +
                ", title='" + title + '\'' +
                ", consumption_in_wh=" + consumption_in_wh +
                ", source='" + source + '\'' +
                '}';
    }
}

