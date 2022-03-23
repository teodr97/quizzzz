package commons.game.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameAlreadyExistsExceptionTest {

    /**
     *
     */
    @Test
    void getMessage() {
        GameAlreadyExistsException exception = new GameAlreadyExistsException("Test");

        assertEquals("Test", exception.getMessage());
    }
}