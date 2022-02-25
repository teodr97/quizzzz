package server.api;

import commons.Activity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @GetMapping(path = "/get")
    public ResponseEntity<Activity> getActivity() {
        return ResponseEntity.ok(new Activity("Example Activity", 50, "source"));
    }

    @PostMapping(path = "/post")
    public void receiveActivity(@RequestBody Activity activity) {
        System.out.println(activity.toString());
    }
}
