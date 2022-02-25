package server.api;

import commons.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.QuestionRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {

    @Autowired
    private QuestionRepository repository;

    @GetMapping
    public List<Question> getAllQuestions(){
        return (List<Question>) repository.findAll();
    }

    /*@GetMapping("{id}")
    public ResponseEntity<Question> getById(@PathVariable int id) {
        Question question = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question doesn't exist with id: "+ id));
        return ResponseEntity.ok(question);
    }*/
}
