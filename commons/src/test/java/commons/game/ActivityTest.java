package commons.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    Activity activity;

    /**
     *Initializes activity before each test
     */
    @BeforeEach
    private void initialiseTest() {
        activity = new Activity("Test", "Test", 1, "Test");
    }

    /**
     *test autoId setter
     */
    @Test
    void setAutoId() {
        activity.setAutoId(19);
        assertEquals(19, activity.getAutoId());
    }

    /**
     * test image_path setter
     */
    @Test
    void setImage_path(){
        activity.setImage_path("image_path");
        assertEquals("image_path", activity.getImage_path());
    }

    /**
     *test title setter
     */
    @Test
    void setTitle() {
        activity.setTitle("Activity");
        assertEquals("Activity", activity.getTitle());
    }

    /**
     *test consumption_in_wh setter
     */
    @Test
    void setConsumption_in_wh() {
        activity.setConsumption_in_wh(90);
        assertEquals(90, activity.getConsumption_in_wh());
    }

    /**
     * test source setter
     */
    @Test
    void setSource() {
        activity.setSource("source");
        assertEquals("source", activity.getSource());
    }

    /**
     * test autoId getter
     */
    @Test
    void getAutoId() {
        assertEquals(0, activity.getAutoId());
    }

    /**
     * test image_path getter
     */
    @Test
    void getImage_path(){assertEquals("Test", activity.getImage_path());}

    /**
     * test title getter
     */
    @Test
    void getTitle() {
        assertEquals("Test", activity.getTitle());
    }

    /**
     * test consumption_in_wh getter
     */
    @Test
    void getConsumption_in_wh() {
        assertEquals(1, activity.getConsumption_in_wh());
    }

    /**
     * test source getter
     */
    @Test
    void getSource(){assertEquals("Test", activity.getSource());}

    /**
     * test toString method
     */
    @Test
    void testToString() {
        assertEquals("Activity", activity.toString());
    }

    /**
     * test equals method
     */
    @Test
    void testEquals() {
        Activity activity1 = new Activity("Test", "Test", 1, "Test");

        assertEquals(activity1, activity);
    }
}