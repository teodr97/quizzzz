package commons.game.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundExceptionTest {

    /**
     *
     */
    @Test
    void getMessage() {
        NotFoundException exception = new NotFoundException("Test");

        assertEquals("Test", exception.getMessage());
    }
}