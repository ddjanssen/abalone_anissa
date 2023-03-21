package abalonetest;

import abalonecontrol.LocalGame;
import abalonemodel.*;
import abaloneview.Tui;
import abaloneview.View;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class MarkTest {


    String[] twoPlayers = {"a", "b"};
    String[] threePlayers = {"a", "b", "c"};
    String[] fourPlayers = {"a", "b", "c", "d"};
    View view = new Tui();
    Board twoboard;
    Board threeboard;
    Board fourboard;


    /**
     * Set up before each test case.
     */
    @Before
    public void setUp() {
        twoboard = (new LocalGame(twoPlayers, view)).getBoard();
        threeboard = (new LocalGame(threePlayers, view)).getBoard();
        fourboard = (new LocalGame(fourPlayers, view)).getBoard();
    }

    /* General tests */

    /*
    * Test that the class Mark is an enum class.
    * */
    @Test
    public void isEnum() {
        assertTrue(Mark.class.isEnum());
    }

    /*
    * Test the enums of class Mark.
    * */
    @Test
    public void testEnums() {
        assertEquals(Mark.X, Mark.valueOf("X"));
        assertEquals(Mark.B, Mark.valueOf("B"));
        assertEquals(Mark.C, Mark.valueOf("C"));
        assertEquals(Mark.W, Mark.valueOf("W"));
        assertEquals(Mark.R, Mark.valueOf("R"));
    }

    /*
    * Test that Mark getNextPlayerTwo() returns the other mark when
    * there are two players. Test with Mark.B and Mark.W.
    * If the Mark is XX, the method should return XX.
    * */
    @Test
    public void getNextPlayerTwo() {
        final String[] twoPlayers = {"Computer", "Computer"};
        LocalGame game = new LocalGame(twoPlayers, new Tui());
        assertEquals(Mark.W, game.getCurrentPlayer().getMark().getNextPlayerTwo());
        ComputerPlayer computerPlayer = (ComputerPlayer) game.getCurrentPlayer();
        Move move = computerPlayer.makeMove(twoboard);
        assertTrue(twoboard.validMove(move));
        twoboard.executeMove(move);
        game.currentPlayer = game.getNextPlayer();
        assertEquals(Mark.B, game.getCurrentPlayer().getMark().getNextPlayerTwo());
        assertEquals(Mark.X, Mark.X.getNextPlayerTwo());
    }

    /*
    * Test that Mark getNextPlayerThree() returns the other mark when
    * there are three players. Test with Mark.B, Mark.C and Mark.W.
    * If the Mark is XX, the method should return XX.
    * */
    @Test
    public void getNextPlayerThree() {
        final String[] threePlayers = {"Computer", "Computer", "Computer"};
        LocalGame game = new LocalGame(threePlayers, new Tui());
        assertEquals(Mark.C, game.getCurrentPlayer().getMark().getNextPlayerThree());
        ComputerPlayer computerPlayer = (ComputerPlayer) game.getCurrentPlayer();
        Move move = computerPlayer.makeMove(threeboard);
        assertTrue(threeboard.validMove(move));
        threeboard.executeMove(move);
        game.currentPlayer = game.getNextPlayer();
        assertEquals(Mark.W, game.getCurrentPlayer().getMark().getNextPlayerThree());
        game.currentPlayer = game.getNextPlayer();
        assertEquals(Mark.B, game.getCurrentPlayer().getMark().getNextPlayerThree());
        assertEquals(Mark.X, Mark.X.getNextPlayerThree());
    }

    /*
    * Test that Mark getNextPlayerFour() returns the other mark when
    * there are four players. Test with Mark.B, Mark.C, Mark.W and Mark.R.
    * If the Mark is XX, the method should return XX.
    * */
    @Test
    public void getNextPlayerFour() {
        final String[] fourPlayers = {"Computer", "Computer", "Computer", "Computer"};
        LocalGame game = new LocalGame(fourPlayers, new Tui());
        assertEquals(Mark.C, game.getCurrentPlayer().getMark().getNextPlayerFour());
        ComputerPlayer computerPlayer = (ComputerPlayer) game.getCurrentPlayer();
        Move move = computerPlayer.makeMove(fourboard);
        assertTrue(fourboard.validMove(move));
        fourboard.executeMove(move);
        game.currentPlayer = game.getNextPlayer();
        assertEquals(Mark.W, game.getCurrentPlayer().getMark().getNextPlayerFour());
        game.currentPlayer = game.getNextPlayer();
        assertEquals(Mark.R, game.getCurrentPlayer().getMark().getNextPlayerFour());
        game.currentPlayer = game.getNextPlayer();
        assertEquals(Mark.B, game.getCurrentPlayer().getMark().getNextPlayerFour());
        assertEquals(Mark.X, Mark.X.getNextPlayerFour());
    }
}