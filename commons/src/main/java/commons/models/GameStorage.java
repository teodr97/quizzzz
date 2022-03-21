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
     * This javadoc should be edited.
     * @return
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
}
