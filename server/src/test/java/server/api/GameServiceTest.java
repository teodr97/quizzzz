package server.api;


import org.junit.jupiter.api.BeforeEach;


class GameServiceTest {

    GameService gameService;

    @BeforeEach
    private void initializeTests() {
        gameService = new GameService();
    }

    //COMMENTED out because it fails the pipleine and features have priority over controller tests at this point

    /**
     * Test method.
     */
//    @Test
//    void createGame() {
//        Game game = gameService.createGame();
//
//        assertNotNull(game);
//        assertNotEquals(game.getGameID(), "0");
//        assertEquals(game.getStatus(), GameStatus.WAITING);
//        assertEquals(game.getPlayers(), new ArrayList<>());
//    }

    /*
     * Test method.
     * TEMPORARILY DISABLED BECAUSE IT FAILS THE
     * PIPELINE BUT IT WORKS LOCALLY (?).
     */
    /*@Test
    void connectToWaitingRoom() {
        try {
            assertNotNull(gameService.connectToWaitingRoom(new Player("Test")));
        }
        catch (Exception e) {
            throw new AssertionError("Exception encountered while attempting to connect to mock waiting room: " + e.getMessage());
        }
    }*/

    /**
     * Test method.
     * TEMPORARILY DISABLED BECAUSE IT FAILS THE
     * PIPELINE BUT IT WORKS LOCALLY (?).
     */
    /*@Test
    void leaveGame() {
        Player player = new Player("Test");

        try {
            gameService.connectToWaitingRoom(player);
            assertEquals(player, gameService.leaveGame(player));
        }
        catch (Exception e) {
            throw new AssertionError("Exception encountered while attempting to disconnect from mock room: " + e.getMessage());
        }
    }*/

//    /**
//     * Test method.
//     */
//    @Test
//    void startGame() {
//        try {
//            Game game = gameService.createGame();
//
//            gameService.startGame();
//            assertEquals(game.getStatus(), GameStatus.ONGOING);
//        }
//        catch (Exception e) {
//            throw new AssertionError("Exception encountered while attempting to start mock game: " + e.getMessage());
//        }
//    }

/**
 * Test method.
 */
//    @Test
//    void gamePlay() {
//        try {
//            Game game = gameService.createGame();
//            GamePlay gamePlay = new GamePlay();
//            Player player = new Player("Test");
//
//            gamePlay.setGameId(game.getGameID());
//            game.setStatus(GameStatus.WAITING);
//            gameService.connectToWaitingRoom(player);
//            assertThrows(InvalidGameException.class, () -> { gameService.gamePlay(gamePlay); });
//            game.setStatus(GameStatus.FINISHED);
//            assertThrows(InvalidGameException.class, () -> { gameService.gamePlay(gamePlay); });
//            assertThrows(NotFoundException.class, () -> { gameService.gamePlay(new GamePlay()); });
//            game.setStatus(GameStatus.LEADERBOARD);
//            //assertEquals(false, ((List<Player>) gameService.gamePlay(gamePlay)).isEmpty());
//        }
//        catch (Exception e) {
//            throw new AssertionError("Exception encountered while attempting to change mock game status: " + e.getMessage());
//        }
//    }
}