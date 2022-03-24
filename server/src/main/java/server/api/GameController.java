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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@RestController
@Slf4j
@RequestMapping("/game")
public class GameController {
    private final GameService gameService = new GameService();
    private final List<Player> playerStore = new ArrayList<>();


    /**
     * @param player creaets a new game
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
     * A player leaves the game.
     * @param player
     * @return
     * @throws NotFoundException
     */
     @PostMapping("/leave")
    public ResponseEntity<Player> leave(@RequestBody Player player) throws NotFoundException{
        return ResponseEntity.ok(gameService.leaveGame(player));
    }
    
    //get the players in a long polling fashion
    //we keep the request open untill a new player connects in which case we
    //send the new array of all players

    /**
     * @param playerid takes the player id and ask the server if there is this player id in the a certain game
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/getPlayers/{id}")
    public ResponseEntity<List<String>> getPlayers(@PathVariable("id") String playerid) throws InterruptedException {
        int playeridint = parseInt(playerid);
        if (lastStoredPlayer().isPresent() && lastStoredPlayer().get().getWaitingRoomId() > playeridint) {
            List<String> output = new ArrayList<>();
            for (int index = playeridint; index < playerStore.size(); index++) {
                output.add(playerStore.get(index).getNickname());
            }
            return ResponseEntity.ok(output);
        }
        System.out.println("poll:");

        return keepPolling(playerid);
    }


    /**
     * @param playerid if the server doesn't have that id for a player recursively request the server every second
     * @return
     * @throws InterruptedException
     */
    private ResponseEntity<List<String>> keepPolling(String playerid) throws InterruptedException {
        Thread.sleep(1000);
        return getPlayers(playerid);

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
