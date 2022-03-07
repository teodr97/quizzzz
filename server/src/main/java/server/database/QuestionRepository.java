package server.database;

import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<commons.game.Question, Integer> {

}
