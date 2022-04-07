package server.api;

import commons.game.Activity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ActivityRepository;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/activity")
public class ActivityController {

    /**
     * A reference to the object used for querying the database.
     */
    private final ActivityRepository repository;

    private ArrayList<Activity> activityList;


    public ActivityController(ActivityRepository repo) {
        this.repository = repo;
    }

    /**
     * Get all activities stored within the database.
     * @return list of activities in DB
     */
    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        return ResponseEntity.ok((List<Activity>) repository.findAll());
    }

    /**
     * Get the amount of activities in the database
     * @return the amount of activities in the database
     */
    int getActivitiesSize() {
        if (activityList == null || activityList.isEmpty()) activityList = (ArrayList<Activity>) repository.findAll();
        return activityList.size();
    }

    /**
     * This function returns a set of three random questions upon request from
     * the client.
     * @return List of three random, different activities from the database
     */
    @GetMapping("/get/randomSetActivities")
    public ResponseEntity<List<Activity>> retrieveRandomActivitiesSet() {
        List<Activity> activitySet = new LinkedList<>();
        List<Integer> alreadyChosenIndexes = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            int index = (int)(Math.random() * getActivitiesSize());

            while (alreadyChosenIndexes.contains(index)) index = (int)(Math.random() * getActivitiesSize());
            activitySet.add(activityList.get(index));
            alreadyChosenIndexes.add(index);
        }
        return ResponseEntity.ok(activitySet);
    }

    /**
     * @return provide client with all activites fromt he repository
     */
    //for testing
    @GetMapping("/get/Activities")
    public ResponseEntity<ArrayList<Activity>> getActivities(){
        activityList = (ArrayList<Activity>) repository.findAll();
        return ResponseEntity.ok(activityList);
    }

    /**
     * Get the activity corresponding to an ID.
     * @return activity corresponding to ID in the URI
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Activity> getById(@PathParam("id") int id) {
        Activity activity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Activity doesn't exist with id: " + id));
        return ResponseEntity.ok(activity);
    }

    /**
     * Creates a new activity that will be stored into the database.
     * @param activity the activity to be inserted
     */
    @PostMapping(path = "/post")
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        Activity act = repository.save(activity);
        return ResponseEntity.ok(act);
    }

    /**
     * Deletes all activities in the database
     */
    @DeleteMapping(path = "/delete/all")
    public void deleteAll(){
        repository.deleteAll();
    }

    /**
     * updates an activity using fields of a given activity
     */
    @PostMapping(path = "/update")
    public ResponseEntity<Activity> update(Activity activity){
        Activity repoActivity = repository.findById(activity.getAutoId()).get();
        repoActivity.setTitle(activity.getTitle());
        repoActivity.setConsumption_in_wh(activity.getConsumption_in_wh());
        repoActivity.setSource(activity.getSource());
        repository.save(repoActivity);
        return ResponseEntity.ok(repoActivity);

    }
}
