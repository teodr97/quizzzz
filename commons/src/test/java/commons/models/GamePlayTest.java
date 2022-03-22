package commons.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GamePlayTest {

    GamePlay gamePlay;
    Game game;

    /**
     * The initializer that runs before each test.
     */
    @BeforeEach
    private void initialiseTests() {
        game = new Game();
        gamePlay = new GamePlay();
        gamePlay.setGameId(game.getGameID());
    }

    /**
     * Test method.
     */
    @Test
    void getGameId() {
        assertEquals(game.getGameID(), gamePlay.getGameId());
    }

    /**
     * Test method.
     */
    @Test
    void setGameId() {
        gamePlay.setGameId("Test");
        assertEquals("Test", gamePlay.getGameId());
    }

    /**
     * Test method.
     */
    @Test
    void getPlayers() {
        assertTrue(gamePlay.getPlayers().isEmpty());
    }

    /**
     * Test method.
     */
    @Test
    void setPlayers() {
        List<Player> playerList = new LinkedList<>();

        playerList.add(new Player("Test"));
        gamePlay.setPlayers(playerList);
        assertEquals(playerList, gamePlay.getPlayers());
    }
}