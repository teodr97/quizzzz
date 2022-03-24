package server.api;

import commons.game.Emote;
import commons.game.exceptions.NotFoundException;
import commons.models.Game;
import commons.models.GameStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/emotes")
public class EmoteController {

    private final GameService gameService;

    @Autowired
    public EmoteController(GameService gameService) {
        this.gameService = gameService;
    }

    //TODO testing
    /**
     * Sends an emote from a user to be displayed to all players. When this is called
     * the emote is delegated to all players in the game on the server. To be retrieved
     * a request to getEmotes() should be sent from the client.
     * @param nickname The nickname of the player.
     * @param emote The emote sent by the player. It doesn't matter if it has a sender
     *              set because it's set here as well
     * @throws NotFoundException Thrown if no game matching the player nickname is found.
     */
    @PostMapping("/send")
    public void sendEmotes(@RequestBody String nickname, Emote emote) throws NotFoundException {
        Game game = GameStorage.getGameByNickname(nickname);
        if (game == null) throw new NotFoundException("No game matches the player nickname.");
        emote.setSender(nickname);

        for (var p : game.getPlayers()) {
            p.addEmote(emote);
        }
    }

    //TODO testing
    /**
     * Gets all emotes that are pending to be displayed by the client. Once requested
     * the pending emotes are reset on the client side.
     * @param nickname The nickname of the player.
     * @return A list of all emotes that haven't been displayed by the client.
     * @throws NotFoundException Thrown if no game matching the player nickname is found.
     */
    @GetMapping("/get")
    public ResponseEntity<List<Emote>> getEmotes(String nickname) throws NotFoundException {
        Game game = GameStorage.getGameByNickname(nickname);
        if (game == null) throw new NotFoundException("No game matches the player nickname.");
        var player = game.getPlayers().stream().filter(x -> x.getNickname().equals(nickname)).findFirst().get();
        List<Emote> emotes = player.getPendingEmotes();
        player.resetPendingEmotes();
        return ResponseEntity.ok(emotes);
    }
}
