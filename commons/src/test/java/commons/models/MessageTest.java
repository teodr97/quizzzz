package commons.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    private Message message;

    @BeforeEach
    private void initializeTests() {
        message = new Message(MessageType.QUESTION, "TestUsername", "TestContent");
    }

    @Test
    void getMsgType() {
        assertEquals(MessageType.QUESTION, message.getMsgType());
    }

    @Test
    void setMsgType() {
        message.setMsgType(MessageType.USER_EMOTE);
        assertEquals(MessageType.USER_EMOTE, message.getMsgType());
    }

    @Test
    void getUsername() {
        assertEquals("TestUsername", message.getUsername());
    }

    @Test
    void setNickname() {
        message.setNickname("TestTest");
        assertEquals("TestTest", message.getUsername());
    }

    @Test
    void getContent() {
        assertEquals("TestContent", message.getContent());
    }

    @Test
    void setContent() {
        message.setContent("TestTestContent");
        assertEquals("TestTestContent", message.getContent());
    }

    @Test
    void testToString() {
        assertEquals("TestUsername(QUESTION): TestContent", message.toString());
    }
}