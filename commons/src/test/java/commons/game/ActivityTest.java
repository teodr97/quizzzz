package commons.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    Activity activity;

    /**
     *
     */
    @BeforeEach
    private void initialiseTest() {
        activity = new Activity("Test", "Test", 1, "Test");
    }

    /**
     *
     */
    @Test
    void setId() {
        activity.setAutoId(19);
        assertEquals(19, activity.getAutoId());
    }

    /**
     *
     */
    @Test
    void setActivity() {
        activity.setTitle("Activity");
        assertEquals("Activity", activity.getTitle());
    }

    /**
     *
     */
    @Test
    void setPower() {
        activity.setConsumption_in_wh(90);
        assertEquals(90, activity.getConsumption_in_wh());
    }

    /**
     *
     */
    @Test
    void getId() {
        assertEquals(0, activity.getAutoId());
    }

    /**
     *
     */
    @Test
    void getActivity() {
        assertEquals("Test", activity.getTitle());
    }

    /**
     *
     */
    @Test
    void getPower() {
        assertEquals(1, activity.getConsumption_in_wh());
    }

    /**
     *
     */
    @Test
    void testToString() {
        assertEquals("{Activity: Test | Power: 1 | Id: 0}", activity.toString());
    }

    /**
     *
     */
    @Test
    void testEquals() {
        Activity activity1 = new Activity("Test", "Test", 1, "Test");

        assertEquals(activity1, activity);
    }
}