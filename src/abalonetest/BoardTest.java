package abalonetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import abalonecontrol.LocalGame;
import abalonemodel.Board;
import abalonemodel.Field;
import abalonemodel.Mark;
import abalonemodel.Move;
import abaloneview.Tui;
import abaloneview.View;

import org.junit.Before;
import org.junit.Test;



public class BoardTest {

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

    @Test
    public void isField() {
        assertTrue(twoboard.isField(11));
        assertTrue(twoboard.isField(99));
        assertFalse(twoboard.isField(1));
        for (Field f : twoboard.getFields()) {
            assertTrue(twoboard.isField(f.getIndex()));
        }
    }

    @Test
    public void testMoveMarble() {
        int[] marbles1 = {76};

        Move correctMove1 = new Move(marbles1,3);

        // move is correct with 1 marble
        assertTrue(twoboard.validMove(correctMove1));

        Move incorrectMove1 = new Move(marbles1,1);
        //nextfield is not empty
        assertFalse(twoboard.validMove(incorrectMove1));

        marbles1[0] = 89;
        Move incorrectMove2 = new Move(marbles1,1);
        // nextfield is not a field
        assertFalse(twoboard.validMove(incorrectMove2));

        marbles1[0] = 34;
        Move incorrectMove3 = new Move(marbles1,2);
        // marble is not from currentplayer
        assertFalse(twoboard.validMove(incorrectMove3));


        int[] marbles2 = {75,76};
        Move correctMove2 = new Move(marbles2,2);
        // a correct move with 2 marbles next to each other
        assertTrue(twoboard.validMove(correctMove2));

        marbles2[1] = 77;
        Move incorrectMove4 = new Move(marbles2,2);
        // an invalid move with 2 marbles not next to each other
        assertFalse(twoboard.validMove(incorrectMove4));

        twoboard.getField(65).setMark(Mark.W);
        // same move as correctMove2, but now there is an opponent marble in the way
        assertFalse(twoboard.validMove(correctMove2));
        twoboard.getField(65).setMark(Mark.X);

        int[] marbles3 = {75,76,77};
        Move correctMove3 = new Move(marbles3,2);
        // a move where 3 marbles are move to downright
        assertTrue(twoboard.validMove(correctMove3));

        int[] marbles4 = {75,76,77,78};
        Move incorrectMove5 = new Move(marbles4, 3);
        twoboard.getField(78).setMark(Mark.B);
        // cannot move more than 3 marbles
        assertFalse(twoboard.validMove(incorrectMove5));
    }

    @Test
    public void testPushMarble2players() {
        int[] marbles2 = {53,52};
        twoboard.getField(53).setMark(Mark.B);
        twoboard.getField(52).setMark(Mark.B);
        twoboard.getField(51).setMark(Mark.B);
        twoboard.getField(54).setMark(Mark.W);
        Move push1 = new Move(marbles2,1);
        // 2 marbles can push 1
        assertTrue(twoboard.validMove(push1));

        twoboard.getField(55).setMark(Mark.W);
        //2 marbles cannot push 2 marbles
        assertFalse(twoboard.validMove(push1));

        int[] marbles3 = {53,52,51};
        Move push2 = new Move(marbles3,1);
        // 3 marbles can push 2 marbles
        assertTrue(twoboard.validMove(push2));

        twoboard.getField(56).setMark(Mark.W);
        assertFalse(twoboard.validMove(push2));

        //now we test the sumito for 2 players
        twoboard.getField(53).setMark(Mark.B);
        twoboard.getField(52).setMark(Mark.B);
        twoboard.getField(54).setMark(Mark.B);
        twoboard.getField(51).setMark(Mark.W);
        int[] marbles4 = {53,54,52};
        Move sumito1 = new Move(marbles4,4);
        // 3 marbles can push 1 of the board
        assertTrue(twoboard.validMove(sumito1));

        twoboard.getField(52).setMark(Mark.W);
        twoboard.getField(55).setMark(Mark.B);

        int[] marbles5 = {53,54,55};
        Move sumito2 = new Move(marbles5,4);
        assertTrue(twoboard.validMove(sumito2));

        int[] marbles6 = {53,54};
        Move sumito3 = new Move(marbles6,4);
        // 2 marbles cannot sumito 2 other marbles
        assertFalse(twoboard.validMove(sumito3));

        // test if the push acually pushes the marbles correctly
        twoboard.getField(51).setMark(Mark.B);
        twoboard.getField(52).setMark(Mark.B);
        twoboard.getField(53).setMark(Mark.B);
        twoboard.getField(54).setMark(Mark.W);
        twoboard.getField(55).setMark(Mark.X);
        int[] marbles7 = {51,52,53};
        Move push3 = new Move(marbles7,1);
        twoboard.executeMove(push3);
        assertEquals(Mark.X, twoboard.getField(51).getMark());
        assertEquals(Mark.B, twoboard.getField(54).getMark());
        assertEquals(Mark.W, twoboard.getField(55).getMark());


    }

    @Test
    public void testPushMarble4players() {
        fourboard.getField(53).setMark(Mark.B);
        fourboard.getField(52).setMark(Mark.W);
        fourboard.getField(51).setMark(Mark.B);
        fourboard.getField(54).setMark(Mark.C);
        fourboard.getField(55).setMark(Mark.R);
        int[] marbles1 = {53,52,51};

        Move push1 = new Move(marbles1,1);
        // 3 marbles with leading mark of currentplayer can move 2 opponent marks
        assertTrue(fourboard.validMove(push1));


        fourboard.getField(52).setMark(Mark.B);
        fourboard.getField(53).setMark(Mark.W);
        //currentplayer cannot engage a push with teamplayer marble
        assertFalse(fourboard.validMove(push1));
    }

    @Test
    public void testSumito() {
        // sets marbles on the board
        twoboard.getField(53).setMark(Mark.B);
        twoboard.getField(52).setMark(Mark.B);
        twoboard.getField(54).setMark(Mark.B);
        twoboard.getField(51).setMark(Mark.W);
        int[] marbles1 = {53,54,52};
        Move sumito1 = new Move(marbles1,4);
        //black player scores a point
        twoboard.executeMove(sumito1);
        assertEquals(Mark.B, twoboard.getField(51).getMark());
        assertEquals(1, twoboard.game.getCurrentPlayer().getPoints());
        assertEquals(1, twoboard.getWinners().size());
        assertEquals(twoboard.game.getCurrentPlayer(), twoboard.getWinners().get(0));

        twoboard.game.currentPlayer = twoboard.game.getNextPlayer();
        assertEquals(Mark.W, twoboard.game.getCurrentPlayer().getMark());
        twoboard.getField(56).setMark(Mark.W);
        twoboard.getField(57).setMark(Mark.W);
        twoboard.getField(58).setMark(Mark.W);
        twoboard.getField(59).setMark(Mark.B);
        int[] marbles2 = {56,57,58};
        Move sumito2 = new Move(marbles2,1);
        // white players scores a point
        twoboard.executeMove(sumito2);

        assertEquals(1, twoboard.game.getCurrentPlayer().getPoints());
        assertEquals(2, twoboard.getWinners().size());
    }


    @Test
      public void testgetWinners() {
        twoboard.game.players[0].setPoints(3);
        twoboard.game.players[1].setPoints(2);
        assertEquals(1, twoboard.getWinners().size());
        assertEquals(twoboard.game.players[0], twoboard.getWinners().get(0));
        twoboard.game.players[0].setPoints(2);
        assertEquals(2, twoboard.getWinners().size());
    
        // for 3 players: initially it ends in a draw
        assertEquals(3, threeboard.getWinners().size());
    
        threeboard.game.players[0].setPoints(3);
        threeboard.game.players[1].setPoints(2);
        threeboard.game.players[2].setPoints(1);
        assertEquals(1, threeboard.getWinners().size());
        assertEquals(threeboard.game.players[0], threeboard.getWinners().get(0));
        // second player wins
        threeboard.game.players[1].setPoints(4);
        assertEquals(1, threeboard.getWinners().size());
        assertEquals(threeboard.game.players[1], threeboard.getWinners().get(0));
    
        // 3th player wins
        threeboard.game.players[2].setPoints(5);
        assertEquals(1, threeboard.getWinners().size());
        assertEquals(threeboard.game.players[2], threeboard.getWinners().get(0));
    
        // player1 and player3 win
        threeboard.game.players[0].setPoints(5);
        assertEquals(2, threeboard.getWinners().size());
        assertTrue(threeboard.getWinners().contains(threeboard.game.players[0]));
        assertTrue(threeboard.getWinners().contains(threeboard.game.players[2]));
    
        // for 4 players:
        assertEquals(4, fourboard.getWinners().size());
        //teamA has more points than TeamB
        fourboard.game.players[0].setPoints(2);
        fourboard.game.players[1].setPoints(2);
        fourboard.game.players[2].setPoints(2);
        fourboard.game.players[3].setPoints(1);
        assertEquals(4, fourboard.getTeamAPoints());
        assertEquals(3, fourboard.getTeamBPoints());
        assertEquals(2, fourboard.getWinners().size());
    }
}
