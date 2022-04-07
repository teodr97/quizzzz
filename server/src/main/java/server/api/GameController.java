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


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@Slf4j
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;
    private final List<Player> playerStore = new ArrayList<>();

    @Autowired
    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    /**
     * @param player create a game and add player
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


        System.out.println("player: "+player.getNickname()+  " connected");

        player.setWaitingRoomId(playerStore.size()+1);
        playerStore.add(player);
        return ResponseEntity.ok(gameService.connectToWaitingRoom(player));
    }

    /**
     * Adds a random player to a game for testing purposes
     * @return The game the player was added to
     * @throws NicknameTakenException
     * @throws NotFoundException
     * @throws GameAlreadyExistsException
     */
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
     * Add point to player
     * @param player
     * @return
     * @throws NotFoundException
     */
    @GetMapping("/addPoint")
    public ResponseEntity<Integer> addPoint(@RequestParam String player, String point) throws NotFoundException{
        int points = Integer.parseInt(point);
        return ResponseEntity.ok(gameService.incrementPoint(points, player));
    }

    /**
     * @param player
     * @return the player with id playerid
     * @throws InterruptedException
     */
    //get the players in a long polling fashion
    //we keep the request open untill a new player connects in which case we
    //send the new array of all players
    @GetMapping("/getPlayers")
    public ResponseEntity<List<Player>> getPlayers(@RequestParam String player) throws InterruptedException, NotFoundException {
        System.out.println("poll:");
        return ResponseEntity.ok(gameService.getPlayers(player));
    }

    /**
     * @return the number of players
     * @throws InterruptedException
     */
    //get the players in a long polling fashion
    //we keep the request open untill a new player connects in which case we
    //send the new array of all players
    @GetMapping("/getAllPlayers")
    public ResponseEntity<Integer> getAllPlayers() throws InterruptedException {
        return ResponseEntity.ok(gameService.getAllPlayers());
    }



    /**
     * @return returns last stored player in the playerstore arraylist if it exists
     */
    private Optional<Player> lastStoredPlayer() {
        return playerStore.isEmpty() ? Optional.empty() : Optional.of(playerStore.get(playerStore.size()-1));
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
