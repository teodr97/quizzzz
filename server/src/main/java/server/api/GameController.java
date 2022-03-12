package server.api;

import commons.game.exceptions.GameAlreadyExistsException;
import commons.game.exceptions.NicknameTakenException;
import commons.models.Game;
import commons.models.GamePlay;
import commons.models.Player;
import commons.game.exceptions.InvalidGameException;
import commons.game.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService = new GameService();

    //creates game
    @PostMapping("/create")
    public ResponseEntity<Game> create(@RequestBody Player player) throws NotFoundException, GameAlreadyExistsException {
        //log.info("started game with players: {}", players);
        return ResponseEntity.ok(gameService.createGame(player));
    }

    //connects player to waiting room
    @PostMapping("/connect")
    public ResponseEntity<Game> connect(@RequestBody Player player) throws NicknameTakenException, NotFoundException, GameAlreadyExistsException {
        //log.info("connect random {}", player);
        return ResponseEntity.ok(gameService.connectToWaitingRoom(player));
    }

    //starts a game
    @PostMapping("/start")
    public ResponseEntity<Game> start(@RequestBody Player player) throws NotFoundException{
        //log.info("game started by: {}", player);
        return ResponseEntity.ok(gameService.startGame());
    }

    @PostMapping("/gameplay")
    public ResponseEntity<Game> gamePlay(@RequestBody GamePlay request) throws NotFoundException, InvalidGameException {
        //log.info("gameplay: {}", request);y
        Game game = gameService.gamePlay(request);
        return ResponseEntity.ok(game);
    }
}
