package server.api;

import commons.models.Game;

import commons.models.GameStorage;
import commons.models.Player;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;


import static org.mockito.Mockito.*;

class GameControllerTest {

    GameController controller;
    GameController spyController;
    @Mock
    Player playerMock;
    @Mock
    GameService gameServiceMock;
    @Mock
    GameStorage gameStorageMock;
    HashMap<String, Game> map = new HashMap<>();
    ResponseEntity<Map<String, Game>> response = ResponseEntity.ok(map);

    /**
     * Initialization for test methods.
     */
    @BeforeEach
    private void initializeTests() {
        MockitoAnnotations.openMocks(this);
        controller = new GameController(gameServiceMock);
        spyController = spy(controller);
        map.put("Test", new Game());
    }

//    /**
//     * Test method.
//     */
//    @Test
//    void create() {
//        when(spyController.getAllGames()).thenReturn(response);
//        try { spyController.create(playerMock);}
//        catch (Exception e) { throw new AssertionError("Exception encountered when creating new game with
//        mock player instance: " + e.getMessage()); }
//
//        verify(gameServiceMock).createGame();
//        assertNotEquals(true, spyController.getAllGames().getBody().isEmpty());
//    }
//
//    /**
//     * Test method.
//     */
//    @Test
//    void getAllGames() {
//        when(spyController.getAllGames()).thenReturn(response);
//        assertNotEquals(true, spyController.getAllGames().getBody().isEmpty());
//    }
//
//    /**
//     * Test method.
//     */
//    @Test
//    void connect() {
//        try {
//            controller.connect(playerMock);
//            verify(gameServiceMock).connectToWaitingRoom(playerMock);
//        }
//        catch (Exception e) {
//            throw new AssertionError("Exception encountered when attempting to connect to game using mock player: " + e.getMessage());
//        }
//    }
//
//    /**
//     * Test method.
//     */
//    @Test
//    void leave() {
//        try {
//            controller.connect(playerMock);
//            controller.leave(playerMock);
//            verify(gameServiceMock).connectToWaitingRoom(playerMock);
//            verify(gameServiceMock).leaveGame(playerMock);
//        }
//        catch (Exception e) {
//            throw new AssertionError("Exception encountered when attempting to disconnect from game using mock player: " + e.getMessage());
//        }
//    }
//
//    /**
//     * Test method.
//     */
//    @Test
//    void start() {
//        try {
//            controller.start(playerMock);
//            verify(gameServiceMock).startGame();
//        }
//        catch (Exception e) {
//            throw new AssertionError("Exception encountered when attempting to start game using mock player: " + e.getMessage());
//        }
//    }
//
//    /**
//     * Test method.
//     */
//    @Test
//    void gamePlay() {
//        try {
//            GamePlay gamePlay = new GamePlay();
//
//            controller.start(playerMock);
//            controller.gamePlay(gamePlay);
//            verify(gameServiceMock).startGame();
//            verify(gameServiceMock).gamePlay(gamePlay);
//        }
//        catch (Exception e) {
//            throw new AssertionError("Exception encountered when attempting to start game using mock player: " + e.getMessage());
//        }
//    }
}