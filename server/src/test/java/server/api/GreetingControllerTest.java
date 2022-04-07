package server.api;

import commons.models.Message;
import commons.models.MessageType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompSession;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GreetingControllerTest {

    GreetingController greetingController;
    GreetingController spyController;

    @Mock
    SimpMessagingTemplate template;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Mock
    StompSession session;

    @BeforeEach
    private void initializeTests() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        greetingController = new GreetingController();
        spyController = Mockito.spy(greetingController);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void greeting() {
        assertEquals(new Message(MessageType.CONNECTED, "Server", "We see you have connected, "),
                greetingController.greeting(new Message(MessageType.TEST, "Test", "Test")));
    }

    @Test
    void start() {
        assertEquals(new Message(MessageType.GAME_STARTED, "Server", "Someone started the game"),
                greetingController.start(new Message(MessageType.TEST, "Test", "Test")));
    }

    /*@Test
    void sendQuestion() {
        greetingController.start(new Message(MessageType.TEST, "Test", "Test"));
        greetingController.sendQuestion();
        assertTrue(outContent.toString().contains("sent a question"));
    }*/

    @Test
    void handleJoker() {
        Message msg = new Message(MessageType.TEST, "Test", "Test");

        assertEquals(new Message(MessageType.TEST, "Server", "Test"),
                greetingController.handleJoker(msg));
    }

    @AfterEach
    private void endTests() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}