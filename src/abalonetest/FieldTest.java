package abalonetest;

import abalonecontrol.LocalGame;
import abalonemodel.Board;
import abalonemodel.Field;
import abalonemodel.Mark;
import abaloneview.Tui;
import abaloneview.View;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class FieldTest {

    String[] twoPlayers = {"dylan", "anissa"};
    Board board;
    View view = new Tui();

    /**
     * Set up before each test case.
     */
    @Before
    public void setUp() {
        board = (new LocalGame(twoPlayers, view)).getBoard();
    }


    /* General tests */

    /*
    * Test that the field is a Field.
    * */
    @Test
    public void isField() {
        assertTrue(board.isField(11));
        assertTrue(board.isField(99));
        assertFalse(board.isField(1));
        for (Field f : board.getFields()) {
            assertTrue(board.isField(f.getIndex()));
        }
    }

    /*
    * Test the getMark() method. It either returns Mark.X or Mark.B or Mark.C or Mark.W or Mark.R.
    * */
    @Test
    public void getMark() {
        for (Field f : board.getFields()) {
            assertTrue(f.getMark() == Mark.X || f.getMark() == Mark.B || f.getMark() == Mark.C || f.getMark() == Mark.W || f.getMark() == Mark.R);
        }
    }

    /*
    * Test that the setMark() method actually sets the mark.
    * Test with Mark.X, Mark.B, Mark.C, Mark.W and Mark.R.
    * */
    @Test
    public void setMark() {
        for (Field f : board.getFields()) {
            f.setMark(Mark.X);
            assertEquals(f.getMark(), Mark.X);
            f.setMark(Mark.B);
            assertEquals(f.getMark(), Mark.B);
            f.setMark(Mark.C);
            assertEquals(f.getMark(), Mark.C);
            f.setMark(Mark.W);
            assertEquals(f.getMark(), Mark.W);
            f.setMark(Mark.R);
            assertEquals(f.getMark(), Mark.R);
        }
    }

    /*
    * Test the getIndex() method.
    * */
    @Test
    public void getIndex() {
        for (Field f : board.getFields()) {
            assertEquals(f.getIndex(), f.getIndex());
        }
    }

    /*
     * Test that the isEmptyField() is actually marked as empty (Mark.X).
     * */
    @Test
    public void isEmptyField() {
        for (Field f : board.getFields()) {
            assertEquals(f.isEmptyField(), f.getMark() == Mark.X);
        }
    }
}