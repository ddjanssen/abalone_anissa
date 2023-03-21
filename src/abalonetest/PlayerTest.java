package abalonetest;

import abalonecontrol.LocalGame;
import abalonemodel.Board;
import abalonemodel.ComputerPlayer;
import abalonemodel.Mark;
import abalonemodel.Move;
import abaloneview.Tui;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class PlayerTest {
    final String[] twoPlayers = {"human", "Computer"};

    Board board;
    LocalGame game;

    /**
     * Set up before each test case.
     */
    @Before
    public void setUp() {
        game = new LocalGame(twoPlayers, new Tui());
        board = game.getBoard();
    }

    /**
     * General player tests
     */

    /**
     * Test if the player has the correct amount of marbles.
     * also test the points when you set them or use the upPoints method.
     */
    @Test
    public void testPoints() {
        assertEquals(0, game.getCurrentPlayer().getPoints());
        assertEquals(0, game.getNextPlayer().getPoints());
        game.getCurrentPlayer().upPoints();
        assertEquals(1, game.getCurrentPlayer().getPoints());
        game.getCurrentPlayer().setPoints(5);
        assertEquals(5, game.getCurrentPlayer().getPoints());
    }

    /**
     * Test if the name of the player is correct.
     */
    @Test
    public void testName() {
        assertEquals("human", game.getCurrentPlayer().getName());
        assertEquals("Computer", game.getNextPlayer().getName());
    }

    /**
     * Test if the type of the player is correct
     * The first player should be of the type HumanPlayer
     * and the other player should be of the type ComputerPlayer
     */
    @Test
    public void testType() {
        assertEquals("HumanPlayer", game.getCurrentPlayer().getClass().getSimpleName());
        assertEquals("ComputerPlayer", game.getNextPlayer().getClass().getSimpleName());
    }

    /**
     * This test tests if the player has the correct enum mark
     * the first player should have the mark W
     * the second player should have the mark B
     * This test should also test when setting the mark that the mark of the player changes
     */
    @Test
    public void testMark() {
        assertEquals(Mark.B, game.getCurrentPlayer().getMark());
        assertEquals(Mark.W, game.getNextPlayer().getMark());
        game.getCurrentPlayer().setMark(Mark.W);
        assertEquals(Mark.W, game.getCurrentPlayer().getMark());
    }

    /**
     * Test if the partners mark. is correct
     * Test this for every different case, where the player has mark and the partner has the other mark:
     * B -> W
     * W -> B
     * C -> R
     * R -> C
     */
    @Test
    public void testPartnerMark() {
        assertEquals(Mark.W, game.getCurrentPlayer().getPartnerMark());
        assertEquals(Mark.B, game.getNextPlayer().getPartnerMark());
        game.getCurrentPlayer().setMark(Mark.C);
        assertEquals(Mark.R, game.getCurrentPlayer().getPartnerMark());
        game.getCurrentPlayer().setMark(Mark.R);
        assertEquals(Mark.C, game.getCurrentPlayer().getPartnerMark());
    }







    /**
     * Computer specific tests
     */

    /**
     * make a test to test the makeMove method
     */



    /**
     * Human specific tests.
     * It is not possible to do these tests for the human player, because it requires the human to perform inputs
     * This can only be tested manually when doing a coverage test
     */

    /**
     * Test for a ComputerPlayer to determine a move
     * This should test if the move is random each time
     */
    @Test
    public void testDetermineMove() {
        ComputerPlayer computerPlayer = (ComputerPlayer) game.getNextPlayer();
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            moves.add(computerPlayer.determineMove(board));
        }

        List<Move> moves1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            moves1.add(computerPlayer.determineMove(board));
        }

        assertNotEquals(moves, moves1);
    }



    /**
     * Make a test to test the makeMove method on 2 computer players
     * This is the only test where we need to create a new board, game and players
     * It is not possible to test if the move is invalid, because otherwise the test will go in an infinite loop
     */
    @Test
    public void testMakeMove() {
        final String[] twoPlayers = {"Computer", "Computer"};
        LocalGame game = new LocalGame(twoPlayers, new Tui());
        Board board = game.getBoard();
        ComputerPlayer computerPlayer = (ComputerPlayer) game.getCurrentPlayer();
        ComputerPlayer computerPlayer1 = (ComputerPlayer) game.getNextPlayer();
        Move move = computerPlayer.makeMove(board);
        assertTrue(board.validMove(move));
        board.executeMove(move);
        game.currentPlayer = game.getNextPlayer();
        Move move1 = computerPlayer1.makeMove(board);
        assertTrue(board.validMove(move1));
    }



}