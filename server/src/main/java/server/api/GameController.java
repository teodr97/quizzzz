package server.api;

import commons.game.exceptions.GameAlreadyExistsException;
import commons.game.exceptions.NicknameTakenException;
import commons.models.Game;
import commons.models.GamePlay;
import commons.models.GameStorage;
import commons.models.Player;
import commons.game.exceptions.InvalidGameException;
import commons.game.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
@AllArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService = new GameService();
    private final List<Player> playerStore = new ArrayList<>();


    //creates game
    @PostMapping("/create")
    public ResponseEntity<Game> create(@RequestBody Player player) throws NotFoundException, GameAlreadyExistsException {
        //log.info("started game with players: {}", players);
        // before jordano edit return ResponseEntity.ok(gameService.createGame(player));
        //to get rid of error
        return ResponseEntity.ok(gameService.createGame());

    }

    @GetMapping
    public ResponseEntity<Map<String, Game>> getAllGames(){
        return ResponseEntity.ok(GameStorage.getGames());
    }

    //connects player to waiting room
    @PostMapping("/connect")
    public ResponseEntity<Game> connect(@RequestBody Player player) throws NicknameTakenException, NotFoundException, GameAlreadyExistsException {
        //log.info("connect random {}", player);
        player.setWaitingRoomId(playerStore.size()+1);
        playerStore.add(player);
        return ResponseEntity.ok(gameService.connectToWaitingRoom(player));
    }

    
    //get the players in a long polling fashion
    //we keep the request open untill a new player connects in which case we
    //send the new array of all players
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

    //keeppolling code
    private ResponseEntity<List<String>> keepPolling(String playerid) throws InterruptedException {
        System.out.println("ff wachten");
        Thread.sleep(5000);
        return getPlayers(playerid);
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.setLocation(URI.create(playerid));
//        return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
    }


    private Optional<Player> lastStoredPlayer() {
        return playerStore.isEmpty() ? Optional.empty() : Optional.of(playerStore.get(playerStore.size()-1));
    }


    @PostMapping("/leave")
    public ResponseEntity<Player> leave(@RequestBody Player player) throws NotFoundException{
        return ResponseEntity.ok(gameService.leaveGame(player));
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
