package server.api;

import commons.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.QuestionRepository;

import java.util.List;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {

    @Autowired
    private QuestionRepository repository;

    @GetMapping
    public List<Question> getAllQuestions(){
        return (List<Question>) repository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Question> getById(@PathVariable int id) {
        Question question = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question doesn't exist with id: "+ id));
        return ResponseEntity.ok(question);
    }

    @PostMapping
    public Question createQuestion(@RequestBody Question question){
        return repository.save(question);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteQuestion(@PathVariable int id){
        Question question = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + id));

        repository.delete(question);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
