package commons.game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    /**
     *tests generateRandomIntSmallerThan method
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
     *tests retrieveActivityLeastEnergy method
     */
    @Test
    void retrieveActivityLeastEnergy() {
        List<Activity> activityList = new LinkedList<>();

        activityList.add(new Activity("Path","Test", 1, "Source"));
        activityList.add(new Activity("Path","Test", 3414, "Source"));
        activityList.add(new Activity("Path","Test", 123, "Source"));
        activityList.add(new Activity("Path","Test", 3, "Source"));
        activityList.add(new Activity("Path","Test", 41, "Source"));
        assertEquals(0, Utils.retrieveActivityLeastEnergy(activityList));
    }

    /**
     *tests retrieveActivityMostEnergy method
     */
    @Test
    void retrieveActivityMostEnergy() {
        List<Activity> activityList = new LinkedList<>();

        activityList.add(new Activity("Path","Test", 1, "Source"));
        activityList.add(new Activity("Path","Test", 3414, "Source"));
        activityList.add(new Activity("Path","Test", 123, "Source"));
        activityList.add(new Activity("Path","Test", 3, "Source"));
        activityList.add(new Activity("Path","Test", 41, "Source"));
        assertEquals(1, Utils.retrieveActivityMostEnergy(activityList));
    }

    /**
     *test replaceActivitiesWithPowerDraws method
     */
    @Test
    void replaceActivitiesWithPowerDraws() {
        ArrayList<Activity> activityList = new ArrayList<>();

        activityList.add(new Activity("Path","Test", 1, "Source"));
        activityList.add(new Activity("Path","Test", 3414, "Source"));
        activityList.add(new Activity("Path","Test", 123, "Source"));
        Utils.replaceActivitiesWithPowerDraws(activityList, 0);
        assertNotEquals("Test", activityList.get(0).getTitle());
        assertNotEquals("Test", activityList.get(1).getTitle());
        assertNotEquals("Test", activityList.get(2).getTitle());

    }
}