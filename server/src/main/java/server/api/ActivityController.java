package server.api;

import server.database.ActivityRepository;
import commons.game.Activity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityRepository repo;

    public ActivityController(ActivityRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = "/get")
    public ResponseEntity<Activity> getActivity() {
        return ResponseEntity.ok(new Activity("Example Activity", 50));
    }

    @PostMapping(path = "/post")
    public ResponseEntity<Activity> receiveActivity(@RequestBody Activity activity) {
        Activity act = repo.save(activity);
        return ResponseEntity.ok(act);
    }
}
