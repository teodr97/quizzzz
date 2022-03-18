package server.api;

import commons.game.exceptions.InvalidGameException;
import commons.game.exceptions.NicknameTakenException;
import commons.game.exceptions.NotFoundException;
import commons.models.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static commons.models.GameStatus.*;

@Service
@AllArgsConstructor
public class GameService {

    //creates a game and sets its status, id, players, etc.
    public Game createGame(){
        Game game = new Game();
        List<Player> players = new ArrayList<>();
        game.setGameID(UUID.randomUUID().toString());
        game.setStatus(WAITING);
        game.setPlayers(players);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    public Game connectToWaitingRoom(Player player) throws NicknameTakenException, NotFoundException{
        Map<String, Game> games = GameStorage.getGames();
        if(games != null){
            for(Game g : games.values()){
                if(g.contains(player.getNickname())){
                    throw new NicknameTakenException("Nickname already taken!");
                }
            }
        }
        Game game = GameStorage.getInstance().getGames().values().stream()
                .filter(it -> it.getStatus().equals(WAITING))
                .findAny().orElse(null);
        if(game == null){
            game = createGame();
        }
        game.addPlayer(player);
        return game;
    }

    public Player leaveGame(Player player) throws NotFoundException{
        Game game = GameStorage.getInstance().getGames().values().stream()
                .filter(it -> it.contains(player.getNickname()))
                .findFirst().orElseThrow(() -> new NotFoundException("No player with username, " + player.getNickname() + " in the game"));
        game.removePlayer(player);
        System.out.println("Player deleted: " + player.getNickname());
        return player;
    }

    public Game startGame() throws NotFoundException{
        Game game = GameStorage.getInstance().getGames().values().stream()
                .filter(it -> it.getStatus().equals(WAITING))
                .findFirst().orElseThrow(() -> new NotFoundException("No waiting room found!"));
        game.setStatus(ONGOING);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    //checks the gameState of the game
    public Game gamePlay(GamePlay gamePlay) throws NotFoundException, InvalidGameException {
        if(!GameStorage.getInstance().getGames().containsKey(gamePlay.getGameId())){
            throw new NotFoundException("Game not found!");
        }

        Game game = GameStorage.getInstance().getGames().get(gamePlay.getGameId());
        if(game.getStatus().equals(FINISHED)){
            throw new InvalidGameException("Game is already finished!");
        }

        if(game.getStatus().equals(WAITING)){
            throw new InvalidGameException("Game has not started yet!");
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
