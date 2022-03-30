package commons.models;

import commons.game.Activity;

/**
 * class for entries we need this in all strings for the admin controller and for the sending of websockets
 */
public class ActivityEntry{
    String autoId;
    String title;
    String source;
    String image_path;
    String consumption_in_wh;

    public ActivityEntry(String autoId, String title, String source, String image_path, String consumption_in_wh){
        this.autoId = autoId;
        this.title = title;
        this.source = source;
        this.image_path = image_path;
        this.consumption_in_wh = consumption_in_wh;
    }

    public String getAutoId() {
        return autoId;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getConsumption_in_wh() {
        return consumption_in_wh;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public void setConsumption_in_wh(String consumption_in_wh) {
        this.consumption_in_wh = consumption_in_wh;
    }

    @Override
    public String toString() {
        return "ActivityEntry{" +
                "autoId='" + autoId +
                ", title='" + title +
                ", source='" + source +
                ", image_path='" + image_path +
                ", consumption_in_wh='" + consumption_in_wh +
                '}';
    }
}