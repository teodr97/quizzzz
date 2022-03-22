package commons.game.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidGameExceptionTest {

    /**
     *
     */
    @Test
    void getMessage() {
        InvalidGameException exception = new InvalidGameException("Test");

        assertEquals("Test", exception.getMessage());
    }
}