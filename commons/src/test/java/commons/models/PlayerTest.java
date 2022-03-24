package commons.models;

import commons.game.Emote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    /**
     * Tests if adding emotes works correctly.
     */
    @Test
    void addingEmotes() {
        Emote emote = new Emote("sender");
        player.addEmote(emote);
        var emotes = player.getPendingEmotes();
        assertEquals(1, emotes.size());
        assertEquals(emote, emotes.get(0));
    }

    /**
     * Tests that resetPendingEmotes() resets the emotes but doesn't erase the previous instance.
     */
    @Test
    void resettingEmotes() {
        Emote emote1 = new Emote("sender1");
        Emote emote2 = new Emote("sender2");
        player.addEmote(emote1);
        player.addEmote(emote2);
        var emotes = player.getPendingEmotes();
        player.resetPendingEmotes();
        assertEquals(0, player.getPendingEmotes().size());
        assertEquals(List.of(emote1, emote2), emotes);
    }
}