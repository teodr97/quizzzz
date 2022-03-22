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

    public void setId(int id) {
        this.id = id;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setConsumption_in_wh(int consumption_in_wh) {
        this.consumption_in_wh = consumption_in_wh;
    }

    public void setSource(String source) {
        this.source = source;
    }


    //GETTERS==========================================================


    public int getId() {
        return id;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getTitle() {
        return title;
    }

    public int getConsumption_in_wh() {
        return consumption_in_wh;
    }

    public String getSource() {
        return source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return consumption_in_wh == activity.consumption_in_wh && Objects.equals(image_path, activity.image_path) && Objects.equals(title, activity.title) && Objects.equals(source, activity.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image_path, title, consumption_in_wh, source);
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", image_path='" + image_path + '\'' +
                ", title='" + title + '\'' +
                ", consumption_in_wh=" + consumption_in_wh +
                ", source='" + source + '\'' +
                '}';
    }
}

