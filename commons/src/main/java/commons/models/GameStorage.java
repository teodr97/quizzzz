package commons.models;

import java.util.HashMap;
import java.util.Map;

public class GameStorage {
    private static Map<String, Game> games;
    private static GameStorage instance;

    private GameStorage(){
        games = new HashMap<>();
    }

    /**
     * Gets the instance of the Game Storage.
     * @return The instance of Game Storage that was created before.
     * If this is the first time it's called, then a new instance is created.
     */
    public static synchronized GameStorage getInstance(){
        if(instance == null){
            instance = new GameStorage();
        }
        return instance;
    }

    /**
     *
     * @return
     */
    public static Map<String, Game> getGames(){
        return games;
    }

    /**
     * Puts a game into the games map, with which we can see how many games are being played
     * @param game
     */
    public static void setGame(Game game){
        games.put(game.getGameID(), game);
    }

    /**
     * Tries to find a game by a player's nickname.
     * @param nickname The nickname of the player whose game we try to find.
     * @return The first game that has a player sharing the given nickname. This assumes that
     * only one game has a player with this nickname, since it will stop after finding the
     * first match. If no matches are found it will return null.
     */
    public static Game getGameByNickname(String nickname) {
        for (String gameId : games.keySet()) {
            Game game = games.get(gameId);
            // check if any of the players in the game have a matching nickname
            if (game.getPlayers().stream().anyMatch(x -> x.getNickname().equals(nickname))) {
                return game;
            }
        }
        return null;
    }
}
