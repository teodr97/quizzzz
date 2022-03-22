package server.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testmapping")
public class TestController {

    /**
     *
     */
    @PostMapping("/post")
    public void postReq() {
        System.out.println("Request received.");
    }

    /**
     * 
     * @return
     */
    @GetMapping("/get")
    public String getReq() {
        return "Get Request Message";
    }
}
