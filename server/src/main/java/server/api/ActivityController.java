package server.api;

import commons.game.Activity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ActivityRepository;
import server.utils.ServerUtils;

import javax.websocket.server.PathParam;
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
     * This function returns a set of three random questions upon request from
     * the client.
     * @return List of three random, different activities from the database
     */
    @GetMapping("/get/randomSetActivities")
    public ResponseEntity<List<Activity>> retrieveRandomActivitiesSet() {
        return ResponseEntity.ok(ServerUtils.retrieveRandomActivities());
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
}
