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
        activity = new Activity("Test", 1);
    }

    /**
     *
     */
    @Test
    void setId() {
        activity.setId(19);
        assertEquals(19, activity.getId());
    }

    /**
     *
     */
    @Test
    void setActivity() {
        activity.setActivity("Activity");
        assertEquals("Activity", activity.getActivity());
    }

    /**
     *
     */
    @Test
    void setPower() {
        activity.setPower(90);
        assertEquals(90, activity.getPower());
    }

    /**
     *
     */
    @Test
    void getId() {
        assertEquals(0, activity.getId());
    }

    /**
     *
     */
    @Test
    void getActivity() {
        assertEquals("Test", activity.getActivity());
    }

    /**
     *
     */
    @Test
    void getPower() {
        assertEquals(1, activity.getPower());
    }

    /**
     *
     */
    @Test
    void testToString() {
        assertEquals("Test", "{Activity: Test | Power: 1 | Id: 0}");
    }

    /**
     *
     */
    @Test
    void testEquals() {
        Activity activity1 = new Activity("Test", 1);

        assertEquals(activity1, activity);
    }
}