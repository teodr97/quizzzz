package commons.models;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WaitingRoomTest {

    @Test
    void getWaitingPlayers() {
        List<Player> playerList = new LinkedList<>();
        WaitingRoom waitingRoom = new WaitingRoom();

        playerList.add(new Player("Test"));
        waitingRoom.setWaitingPlayers(playerList);
        assertEquals(new Player("Test"), waitingRoom.getWaitingPlayers().get(0));
    }
}