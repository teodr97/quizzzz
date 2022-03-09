package server.database;

import org.springframework.data.jpa.repository.JpaRepository;
import commons.game.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
