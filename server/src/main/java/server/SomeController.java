package server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class SomeController {

    /**
     *
     * @return
     */
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Hello world!";
    }

    /**
     *
     * @param string
     * @return
     */
    @ResponseBody
    @GetMapping("/pathTest")
    public String methodPathTest(@RequestParam(name = "name") Optional<String> string) {
        if (string.isEmpty()) return "Test path.";
        return string.get();
    }
}