package commons.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStorageTest {

    /**
     * Test method.
     */
    @Test
    void getInstance() {
        assertNotNull(GameStorage.getInstance());
    }

    /**
     * Test method.
     */
    @Test
    void getGames() {
        GameStorage.getInstance();
        assertTrue(GameStorage.getGames().isEmpty());
    }

    /**
     * Test method.
     */
    @Test
    void setGame() {
        GameStorage.getInstance();
        GameStorage.setGame(new Game());
        assertEquals(1, GameStorage.getGames().size());
    }
}