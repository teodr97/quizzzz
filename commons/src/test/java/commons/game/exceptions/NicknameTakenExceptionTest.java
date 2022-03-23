package commons.game.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NicknameTakenExceptionTest {

    /**
     *
     */
    @Test
    void getMessage() {
        NicknameTakenException exception = new NicknameTakenException("Test");

        assertEquals("Test", exception.getMessage());
    }
}