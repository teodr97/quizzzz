package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/testing")
public class PingController {

    /**
     * Route used for verifying that this server exists.
     * @return The message Pong.
     */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        System.out.println("ping received");
        return ResponseEntity.ok("pong");
    }
}
