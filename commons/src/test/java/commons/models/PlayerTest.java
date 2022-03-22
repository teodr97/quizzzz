package commons.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;

    /**
     * The method that initializes each test.
     */
    @BeforeEach
    private void initialiseTests() {
        player = new Player("Test");
    }

    /**
     * Test method.
     */
    @Test
    void getNickname() {
        assertEquals("Test", player.getNickname());
    }

    /**
     * Test method.
     */
    @Test
    void getPoints() {
        assertEquals(0, player.getPoints());
    }

    /**
     * Test method.
     */
    @Test
    void addPoints() {
        player.addPoints(123);
        assertEquals(123, player.getPoints());
        player.addPoints(28);
        assertEquals(151, player.getPoints());
    }

    /**
     * Test method.
     */
    @Test
    void getChosenAnswer() {
        assertEquals(-1, player.getChosenAnswer());
    }

    /**
     * Test method.
     */
    @Test
    void setChosenAnswer() {
        player.setChosenAnswer(123);
        assertEquals(123, player.getChosenAnswer());
    }

    /**
     * Test method.
     */
    @Test
    void getTimeLeft() {
        assertEquals(1, player.getTimeLeft());
    }

    /**
     * Test method.
     */
    @Test
    void setTimeLeft() {
        player.setTimeLeft(3124);
        assertEquals(3124, player.getTimeLeft());
    }
}