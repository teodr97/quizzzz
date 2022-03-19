package commons.game.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidParamExceptionTest {

    /**
     *
     */
    @Test
    void getMessage() {
        InvalidParamException exception = new InvalidParamException("Test");

        assertEquals("Test", exception.getMessage());
    }
}