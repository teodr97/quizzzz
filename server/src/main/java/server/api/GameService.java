package server.api;

import commons.game.exceptions.InvalidGameException;
import commons.game.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import server.models.Game;
import server.models.GamePlay;
import server.models.GameStorage;
import server.models.Player;

import java.util.List;
import java.util.UUID;

import static server.models.GameStatus.*;

@Service
@AllArgsConstructor
public class GameService {

    //creates a game and sets its status, id, players, etc.
    public Game createGame(List<Player> players){
        Game game = new Game();
        game.setGameID(UUID.randomUUID().toString());
        game.setStatus(ONGOING);
        game.setPlayers(players);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    //checks the gameState of the game
    public Game gamePlay(GamePlay gamePlay) throws NotFoundException, InvalidGameException {
        if(!GameStorage.getInstance().getGames().containsKey(gamePlay.getGameId())){
            throw new NotFoundException("Game not found");
        }

        Game game = GameStorage.getInstance().getGames().get(gamePlay.getGameId());
        if(game.getStatus().equals(FINISHED)){
            throw new InvalidGameException("Game is already finished");
        }

        //if no questions are left, the game is over then we need to show the leaderboard
        if(game.getStatus().equals(LEADERBOARD)){
            leaderBoard(gamePlay.getPlayers());
        }

        return game;
    }

    //returns an array of players in decreasing order based on their points
    private Player[] leaderBoard(List<Player> players){
        Player[] list = new Player[players.size()];
        int max = Integer.MIN_VALUE;
        int index = 0;
        for(int i = 0; i < players.size(); i++){
            for(int o = 0; o < players.size(); o++){
                if(players.get(o).getPoints() > max){
                    max = players.get(o).getPoints();
                    index = o;
                }
            }
            max = Integer.MIN_VALUE;
            list[i] = players.get(index);
        }

        return list;
    }

}
