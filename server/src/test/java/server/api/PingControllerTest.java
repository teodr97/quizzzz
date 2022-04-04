package server.api;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PingControllerTest {

    /**
     * Checks if the ping method returns pong.
     */
    @Test
    public void testPingMethod() {
        var ctrl = new PingController();
        var response = ctrl.ping();
        assertEquals("pong", response.getBody());
    }
}