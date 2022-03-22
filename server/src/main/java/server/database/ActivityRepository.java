package server.database;

//import commons.game.Activity;
//import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<commons.game.Activity, Integer> {

}
