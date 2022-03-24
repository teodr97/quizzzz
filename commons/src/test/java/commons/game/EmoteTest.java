package commons.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmoteTest {

    private static Emote emote;

    /**
     * Before Each
     */
    @BeforeEach
    void setUp() {
        emote = new Emote("sender");
    }

    /**
     * Tests getSender()
     */
    @Test
    void getSender() {
        assertEquals("sender", emote.getSender());

    }

    /**
     * Test setSender()
     */
    @Test
    void setSender() {
        emote.setSender("newsender");
        assertEquals("newsender", emote.getSender());
    }

    /**
     * Test equals()
     */
    @Test
    void testEquals() {
        Emote newEmote = new Emote("sender");
        assertEquals(newEmote, emote);
    }

    /**
     * Test hashCode()
     */
    @Test
    void testHashCode() {
        Emote newEmote = new Emote("sender");
        assertEquals(newEmote.hashCode(), emote.hashCode());
    }
}