package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.QuestionRepository;

import java.util.List;
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
    public String deleteQuestion(@PathVariable int id){
        Question question = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + id));

        repository.delete(question);

        return "Entry deleted";
    }

    @DeleteMapping
    public String deleteAll(){
        repository.deleteAll();
        return "All entries deleted";
    }


}
