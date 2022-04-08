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

    /**
     * Creates a game with a list of Players (Objects) which is empty in the beginning.
     * Adds the game object to GameStorage.
     * @return Returns the game object
     */
    public Game createGame(){
        Game game = new Game();
        List<Player> players = new ArrayList<>();
        game.setGameID(UUID.randomUUID().toString());
        game.setStatus(WAITING);
        game.setPlayers(players);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    /**
     * Connects player to a game that's in WAITING status
     * or creates a new game if no such game exists
     * @param player The player that wants to connect.
     * @return  Returns the game that the player connected to.
     * @throws NicknameTakenException
     * @throws NotFoundException
     */
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

    /**
     * Leaves the current game/waiting room.
     * @param player Player that wants to leave the game.
     * @return Returns the Player that left the game.
     * @throws NotFoundException
     */
    public Player leaveGame(Player player) throws NotFoundException{
        Game game = GameStorage.getInstance().getGames().values().stream()
                .filter(it -> it.contains(player.getNickname()))
                .findFirst().orElseThrow(() -> new NotFoundException("No player with username, " + player.getNickname() + " in the game"));
        game.removePlayer(player);
        System.out.println("Player deleted: " + player.getNickname());
        return player;
    }

    /**
     * Starts the game NOT SET YET!!!
     * @return Returns the started game.
     * @throws NotFoundException
     */
    public Game startGame() throws NotFoundException{
        Game game = GameStorage.getInstance().getGames().values().stream()
                .filter(it -> it.getStatus().equals(WAITING))
                .findAny().orElseThrow(() -> new NotFoundException("No waiting room found!"));
        game.setStatus(ONGOING);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    /**
     * Gets a list of players based on username;
     * @param username The player's username that requested.
     * @return Returns a list of players in the game.
     * @throws NotFoundException
     */
    public List<Player> getPlayers(String username) throws NotFoundException{
        Game game = GameStorage.getInstance().getGames().values().stream()
                .filter(it -> it.contains(username))
                .findAny().orElseThrow(() -> new NotFoundException("No player with username, " + username + " in the game"));
        return game.getPlayers();
    }

    /**
     * Gets number of players on the server.
     * @return Returns an integer
     */
    public Integer getAllPlayers(){
        Map<String, Game> game = GameStorage.getInstance().getGames();
        int players = 0;
        for(Game g : game.values()){
            players += g.getPlayers().size();
        }
        return players;
    }

    /**
     * NOT FULLY IMPLEMENTED YET
     * Should check what gamestate is the game in.
     * @param gamePlay Gets a gamePlay object.
     * @return Returns the game that was checked.
     * @throws NotFoundException
     * @throws InvalidGameException
     */
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

    /**
     * Increments players points
     * @param point
     * @param username
     * @return the incremented point
     * @throws NotFoundException
     */
    public int incrementPoint(int point, String username) throws NotFoundException{
        Game game = GameStorage.getInstance().getGames().values().stream()
                .filter(it -> it.contains(username))
                .findAny().orElseThrow(() -> new NotFoundException("No player with username, " + username + " in the game"));
        for(Player p: game.getPlayers()){
            if(p.getNickname().equals(username)){
                p.addPoints(point);
            }
        }
        return point;
    }

    /**
     * Makes a leaderboard at the end of the game.
     * @param players All the players that were in the game.
     * @return Returns the List of players in order by points.
     */
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
