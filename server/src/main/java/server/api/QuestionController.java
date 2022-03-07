package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import commons.game.Question;
import server.database.QuestionRepository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {

    /**
     * A reference to the object used for querying the database.
     */
    @Autowired
    private QuestionRepository repository;

    /**
     * Get all questions stored within the database.
     * @return list of questions in DB
     */
    @GetMapping
    public List<Question> getAllQuestions(){
        return (List<Question>) repository.findAll();
    }

    /**
     * Get the question corresponding to an ID.
     * @return question corresponding to ID in the URI
     */
    @GetMapping("{id}")
    public ResponseEntity<Question> getById(@PathParam("id") int id) {
        Question question = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question doesn't exist with id: "+ id));
        return ResponseEntity.ok(question);
    }

    /**
     * Creates a new question that will be stored into the database.
     * @param question the question to be inserted
     */
    @PostMapping
    public Question createQuestion(@RequestBody Question question){
        return repository.save(question);
    }

    /**
     * Deletes the question entry in the database associated with
     * the given id.
     * @throws NoSuchElementException if there's no element associated with
     * the id
     * @param id the id associated with the entry
     * @return the Question object that has just been removed from the DB
     */
    @DeleteMapping("{id}")
    public Question deleteQuestion(@PathParam("id") int id){
        Question question = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + id));

        repository.delete(question);

        return question;
    }

    /**
     * Deletes ALL entries within the Questions table. Made private for
     * safety reasons.
     * @return message saying the operation was successful
     */
    @DeleteMapping
    private String deleteAll(){
        repository.deleteAll();
        return "All entries deleted.";
    }


}
