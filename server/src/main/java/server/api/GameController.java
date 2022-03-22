package server.api;

import commons.game.exceptions.GameAlreadyExistsException;
import commons.game.exceptions.InvalidGameException;
import commons.game.exceptions.NicknameTakenException;
import commons.game.exceptions.NotFoundException;
import commons.models.Game;
import commons.models.GamePlay;
import commons.models.GameStorage;
import commons.models.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Creates a new game, started by a given player. That player will be the first one put into the game.
     * @param player
     * @return
     * @throws NotFoundException
     * @throws GameAlreadyExistsException
     */
    //creates game
    @PostMapping("/create")
    public ResponseEntity<Game> create(@RequestBody Player player) throws NotFoundException, GameAlreadyExistsException {
        //log.info("started game with players: {}", players);
        // before jordano edit return ResponseEntity.ok(gameService.createGame(player));
        //to get rid of error
        return ResponseEntity.ok(gameService.createGame());

    }

    /**
     * Gets all ongoing games.
     * @return
     */
    @GetMapping
    public ResponseEntity<Map<String, Game>> getAllGames(){
        return ResponseEntity.ok(GameStorage.getGames());
    }

    /**
     * Connects the player to the current waiting room.
     * @param player
     * @return
     * @throws NicknameTakenException
     * @throws NotFoundException
     * @throws GameAlreadyExistsException
     */
    @PostMapping("/connect")
    public ResponseEntity<Game> connect(@RequestBody Player player) throws NicknameTakenException, NotFoundException, GameAlreadyExistsException {
        //log.info("connect random {}", player);
        return ResponseEntity.ok(gameService.connectToWaitingRoom(player));
    }

    @PostMapping("/random")
    public ResponseEntity<Game> random() throws NicknameTakenException, NotFoundException, GameAlreadyExistsException {
        //log.info("connect random {}", player);
        Player player = new Player("player1");
        return ResponseEntity.ok(gameService.connectToWaitingRoom(player));
    }

    /**
     * A player leaves the game.
     * @param player
     * @return
     * @throws NotFoundException
     */
    @PostMapping("/leave")
    public ResponseEntity<Player> leave(@RequestBody Player player) throws NotFoundException{
        return ResponseEntity.ok(gameService.leaveGame(player));
    }

    /**
     * Starts a game.
     * @param player
     * @return
     * @throws NotFoundException
     */
    @PostMapping("/start")
    public ResponseEntity<Game> start(@RequestBody Player player) throws NotFoundException{
        //log.info("game started by: {}", player);
        return ResponseEntity.ok(gameService.startGame());
    }

    /**
     * Not sure what this is supposed to do. This javadoc should be edited by someone who knows.
     * @param request
     * @return
     * @throws NotFoundException
     * @throws InvalidGameException
     */
    @PostMapping("/gameplay")
    public ResponseEntity<Game> gamePlay(@RequestBody GamePlay request) throws NotFoundException, InvalidGameException {
        //log.info("gameplay: {}", request);y
        Game game = gameService.gamePlay(request);
        return ResponseEntity.ok(game);
    }
}
