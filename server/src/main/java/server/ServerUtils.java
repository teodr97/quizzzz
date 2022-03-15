package server;

import commons.game.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import server.api.ActivityController;
import server.database.ActivityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class ServerUtils {
    /**
     * The list containing every activity;
     */
    private static List<Activity> activityList;

    private static ActivityController repository;

    private static List<Activity> getAllActivities() {
        return repository.getAllActivities().getBody();
    }

    /**
     * Get the amount of activities in the database
     * @return the amount of activities in the database
     */
    private static int getActivitiesSize() {
        activityList = getAllActivities();
        return activityList.size();
    }

    /**
     * Returns a set containing three random activities.
     * The method ensures that the activities retrieved are non-repeating.
     * @return List containing three random non-repeating activities
     */
    public static List<Activity> retrieveRandomActivities() {
        List<Activity> activitySet = new LinkedList<>();
        List<Integer> alreadyChosenIndexes = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            int index = (int)(Math.random() * getActivitiesSize());

            while (alreadyChosenIndexes.contains(index)) index = (int)(Math.random() * getActivitiesSize());
            activitySet.add(activityList.get(index));
            alreadyChosenIndexes.add(index);
        }
        return activitySet;
    }
}
