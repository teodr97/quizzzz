package server;

import commons.game.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import server.database.ActivityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;

public class ServerUtils {
    /**
     * The list containing every activity;
     */
    private List<Activity> activityList;

    @Autowired
    private ActivityRepository repository;

    private List<Activity> getAllActivities() {
        return (List<Activity>) repository.findAll();
    }

    /**
     * Get the amount of activities in the database
     * @return the amount of activities in the database
     */
    public int getActivitiesSize() {
        return activityList.size();
    }

    /**
     * Returns a set containing three random activities.
     * The method ensures that the activities retrieved are non-repeating.
     * @return List containing three random non-repeating activities
     */
    public List<Activity> retrieveRandomActivities() {
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
