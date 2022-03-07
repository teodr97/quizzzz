package server.api;

import commons.game.Activity;
import commons.game.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ActivityRepository;
import server.database.QuestionRepository;

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
    @Autowired
    private ActivityRepository repository;

    /**
     * Get all activities stored within the database.
     * @return list of activities in DB
     */
    @GetMapping
    public List<Activity> getAllActivities() {
        return (List<Activity>) repository.findAll();
    }

    /**
     * Get the activity corresponding to an ID.
     * @return activity corresponding to ID in the URI
     */
    @GetMapping("{id}")
    public ResponseEntity<Activity> getById(@PathParam("id") int id) {
        Activity activity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Activity doesn't exist with id: " + id));
        return ResponseEntity.ok(activity);
    }

    /**
     * Creates a new activity that will be stored into the database.
     * @param activity the activity to be inserted
     */
    @PostMapping
    public Activity createActivity(@RequestBody Activity activity) {
        return repository.save(activity);
    }

    /**
     * Deletes the activity entry in the database associated with
     * the given id.
     * @throws NoSuchElementException if there's no element associated with
     * the id
     * @param id the id associated with the entry
     * @return the Activity object that has just been removed from the DB
     */
    @DeleteMapping("{id}")
    public Activity deleteActivity(@PathParam("id") int id) {
        Activity activity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Activity not found with id: " + id));

        repository.delete(activity);

        return activity;
    }

    /**
     * Deletes ALL entries within the Activities table. Made private for
     * safety reasons.
     * @return message saying the operation was successful
     */
    @DeleteMapping
    private String deleteAll() {
        repository.deleteAll();
        return "All entries deleted.";
    }
}
