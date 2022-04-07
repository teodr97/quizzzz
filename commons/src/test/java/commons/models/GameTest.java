package commons.models;

import commons.game.Activity;

import commons.game.exceptions.NicknameTakenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private Player player;
    private Question question;

    @Mock
    Game.QuestionGenerator questionGeneratorMock;

    /**
     * The initializer method for every test.
     */
    @BeforeEach
    private void initialiseTests() {
        ArrayList<Activity> activityList = new ArrayList<>();

        MockitoAnnotations.openMocks(this);
        activityList.add(new Activity("Test_1", "Test_1", 0, "Test_2"));
        activityList.add(new Activity("Test_2", "Test_2", 2314567, "Test_2"));
        activityList.add(new Activity("Test_3","Test_3", 1010010100, "Test_3"));
        game = new Game();
        question = new Question((ArrayList<Activity>) activityList);
        player = new Player("Test");
    }

    /**
     * Test method.
     */
    @Test
    void getGameID() {
        System.out.println(game.getGameID());
        assertNotEquals("0", game.getGameID());
    }

    /**
     * Test method.
     */
    @Test
    void setGameID() {
        game.setGameID("Test");
        assertEquals("Test", game.getGameID());
    }

    /**
     * Test method.
     */
    @Test
    void getStatus() {
        game.setStatus(GameStatus.ONGOING);
        assertEquals(GameStatus.ONGOING, game.getStatus());
    }

    /**
     * Test method.
     */
    @Test
    void setStatus() {
        game.setStatus(GameStatus.WAITING);
        assertEquals(GameStatus.WAITING, game.getStatus());
        game.setStatus(GameStatus.ONGOING);
        assertEquals(GameStatus.ONGOING, game.getStatus());
    }

    /**
     * Test method.
     */
    @Test
    void setPlayers() {
        List<Player> playerList = new LinkedList<>();

        playerList.add(player);
        game.setPlayers(playerList);
        assertEquals(playerList, game.getPlayers());
    }

    /**
     * Test method.
     */
    @Test
    void getCurRound() {
        assertEquals(1, game.getCurRound());
    }

    /**
     * Test method.
     */
    @Test
    void setCurRound() {
        game.setCurRound(69);
        assertEquals(69, game.getCurRound());
    }

    /**
     * Test method.
     */
    @Test
    void getTotalRounds() {
        assertEquals(20, game.getTotalRounds());
    }

    /**
     * Test method.
     */
    @Test
    void getCurQuestion() {
        assertNull(game.getCurQuestion());
    }

    /**
     * Test method.
     */
    @Test
    void getPlayers() {
        assertTrue(game.getPlayers().isEmpty());
    }

    /**
     * Test method.
     */
    @Test
    void addPlayer() {
        try {
            game.addPlayer(player);
        }
        catch (NicknameTakenException e) {
            throw new AssertionError("Nickname already taken when attempting to add mock player to mock game.");
        }
        assertFalse(game.getPlayers().isEmpty());
    }

    /**
     * Test method.
     */
    @Test
    void removePlayer() {
        try {
            game.addPlayer(player);
            assertFalse(game.getPlayers().isEmpty());
            game.removePlayer(player);
            assertTrue(game.getPlayers().isEmpty());
        }
        catch (Exception e) {
            throw new AssertionError("Nickname already taken when attempting to add/remove mock player to mock game: " + e.getMessage());
        }
    }

    /**
     * Test method.
     */
    @Test
    void contains() {
        try {
            game.addPlayer(player);
            assertTrue(game.contains("Test"));
        }
        catch (Exception e) {
            throw new AssertionError("Nickname already taken when attempting to add mock player to mock game: " + e.getMessage());
        }
    }

    /**
     * Test method.
     */
    @Test
    void endGame() {
        // TO DO
    }

    /**
     * Test method.
     */
    @Test
    void setCurQuestion() {
        game.setCurQuestion(question);
        assertEquals(question, game.getCurQuestion());
    }
}