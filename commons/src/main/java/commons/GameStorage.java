package commons;

import java.util.HashMap;
import java.util.Map;

public class GameStorage {
    private static Map<String, Game> games;
    private static GameStorage instance;

    private GameStorage(){
        games = new HashMap<>();
    }

    public static synchronized GameStorage getInstance(){
        if(instance == null){
            instance = new GameStorage();
        }
        return instance;
    }

    public Map<String, Game> getGames(){
        return games;
    }

    //puts a game into the games map, with which we can see how many games are being played
    public void setGame(Game game){
        games.put(game.getGameID(), game);
    }
}
