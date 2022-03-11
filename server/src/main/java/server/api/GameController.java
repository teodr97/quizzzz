package server.api;

import server.models.Game;
import server.models.GamePlay;
import server.models.Player;
import commons.game.exceptions.InvalidGameException;
import commons.game.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService = new GameService();

    //starts a game
    @PostMapping("/start")
    public ResponseEntity<Game> start(@RequestBody List<Player> players){
        //log.info("started game with players: {}", players);
        return ResponseEntity.ok(gameService.createGame(players));
    }

    //gets the status of the game
    @PostMapping("/gameplay")
    public ResponseEntity<Game> gamePlay(@RequestBody GamePlay request) throws NotFoundException, InvalidGameException {
        //log.info("gameplay: {}", request);y
        Game game = gameService.gamePlay(request);
        return ResponseEntity.ok(game);
    }
}
