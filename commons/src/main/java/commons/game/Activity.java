package commons.game;

//@Entity
public class Activity {

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String title;
    public int consumption_in_wh;
    public String source;

    private Activity() {

    }

    public Activity(String title, int consumption_in_wh, String source) {
        this.title = title;
        this.consumption_in_wh = consumption_in_wh;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

    //    @Override
//    public boolean equals(Object obj) {
//        return EqualsBuilder.reflectionEquals(this, obj);
//    }
//
//    @Override
//    public int hashCode() {
//        return HashCodeBuilder.reflectionHashCode(this);
//    }
//
//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
//    }
}
