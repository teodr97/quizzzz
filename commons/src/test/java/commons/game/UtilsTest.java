package commons.game;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    /**
     *
     */
    @Test
    void generateRandomIntSmallerThan() {
        for (int i = 0; i < 100; i++) {
            int r = Utils.generateRandomIntSmallerThan(100);

            if (r >= 100) throw new AssertionError("At least one number was greater than or equal to 100.");
        }
        assertTrue(true);
    }

    /**
     *
     */
    @Test
    void retrieveActivityLeastEnergy() {
        List<Activity> activityList = new LinkedList<>();

        activityList.add(new Activity("Test", 1));
        activityList.add(new Activity("Test", 3414));
        activityList.add(new Activity("Test", 123));
        activityList.add(new Activity("Test", 3));
        activityList.add(new Activity("Test", 41));
        assertEquals(activityList.get(0), Utils.retrieveActivityLeastEnergy(activityList));
    }

    /**
     *
     */
    @Test
    void retrieveActivityMostEnergy() {
        List<Activity> activityList = new LinkedList<>();

        activityList.add(new Activity("Test", 1));
        activityList.add(new Activity("Test", 3414));
        activityList.add(new Activity("Test", 123));
        activityList.add(new Activity("Test", 3));
        activityList.add(new Activity("Test", 41));
        assertEquals(activityList.get(1), Utils.retrieveActivityMostEnergy(activityList));
    }

    /**
     *
     */
    @Test
    void replaceActivitiesWithPowerDraws() {
        List<Activity> activityList = new LinkedList<>();

        activityList.add(new Activity("Test", 1));
        activityList.add(new Activity("Test", 3414));
        activityList.add(new Activity("Test", 123));
        Utils.replaceActivitiesWithPowerDraws(activityList, 0);
        assertNotEquals("Test", activityList.get(0).getActivity());
        assertNotEquals("Test", activityList.get(1).getActivity());
        assertNotEquals("Test", activityList.get(2).getActivity());

    }
}