package server.database;

import org.springframework.data.repository.CrudRepository;
import server.api.Question;

public interface QuestionRepository extends CrudRepository<Question, Integer> {



}
