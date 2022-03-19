package server.api;

import commons.game.exceptions.InvalidGameException;
import commons.game.exceptions.NotFoundException;
import commons.models.Game;
import commons.models.GamePlay;
import commons.models.GameStatus;
import commons.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    GameService gameService;

    @BeforeEach
    private void initializeTests() {
        gameService = new GameService();
    }

    /**
     * Test method.
     */
    @Test
    void createGame() {
        Game game = gameService.createGame();

        assertNotNull(game);
        assertNotEquals(game.getGameID(), "0");
        assertEquals(game.getStatus(), GameStatus.WAITING);
        assertEquals(game.getPlayers(), new ArrayList<>());
    }

    /**
     * Test method.
     */
    @Test
    void connectToWaitingRoom() {
        try {
            assertNotNull(gameService.connectToWaitingRoom(new Player("Test")));
        }
        catch (Exception e) {
            throw new AssertionError("Exception encountered while attempting to connect to mock waiting room: " + e.getMessage());
        }
    }

    /**
     * Test method.
     */
    @Test
    void leaveGame() {
        Player player = new Player("Test");

        try {
            gameService.connectToWaitingRoom(player);
            assertEquals(player, gameService.leaveGame(player));
        }
        catch (Exception e) {
            throw new AssertionError("Exception encountered while attempting to disconnect from mock room: " + e.getMessage());
        }
    }

    /**
     * Test method.
     */
    @Test
    void startGame() {
        try {
            Game game = gameService.createGame();

            gameService.startGame();
            assertEquals(game.getStatus(), GameStatus.ONGOING);
        }
        catch (Exception e) {
            throw new AssertionError("Exception encountered while attempting to start mock game: " + e.getMessage());
        }
    }

    /**
     * Test method.
     */
    @Test
    void gamePlay() {
        try {
            Game game = gameService.createGame();
            GamePlay gamePlay = new GamePlay();
            Player player = new Player("Test");

            gamePlay.setGameId(game.getGameID());
            game.setStatus(GameStatus.WAITING);
            gameService.connectToWaitingRoom(player);
            assertThrows(InvalidGameException.class, () -> { gameService.gamePlay(gamePlay); });
            game.setStatus(GameStatus.FINISHED);
            assertThrows(InvalidGameException.class, () -> { gameService.gamePlay(gamePlay); });
            assertThrows(NotFoundException.class, () -> { gameService.gamePlay(new GamePlay()); });
            game.setStatus(GameStatus.LEADERBOARD);
            assertEquals(false, ((List<Player>) gameService.gamePlay(gamePlay)).isEmpty());
        }
        catch (Exception e) {
            throw new AssertionError("Exception encountered while attempting to change mock game status: " + e.getMessage());
        }
    }
}