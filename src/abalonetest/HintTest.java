package abalonetest;

import abalonecontrol.LocalGame;
import abalonemodel.Board;
import abalonemodel.Field;
import abalonemodel.Mark;
import abalonemodel.Move;
import abaloneview.Tui;
import abaloneview.View;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class HintTest {

    String[] twoPlayers = {"a", "b"};
    View view = new Tui();
    Board twoboard;

    LocalGame game;

    /**
     * Set up before each test case.
     */
    @Before
    public void setUp() {
        game = new LocalGame(twoPlayers, view);
        twoboard = (game).getBoard();
    }

    @Test
    public void isHint() {
        String board1 = twoboard.toString();
        Move hint = game.currentPlayer.getHint(twoboard);
        ArrayList<Move> all_moves = game.currentPlayer.validMoves(twoboard);
//        Ensure that the board state doesnt change after requesting a hint
        assertTrue(board1.equals(twoboard.toString()));
//        Ensure that the hint exists if there is a move
        if (all_moves.size() == 0) {
            assertNull(hint);
        }else {
//            Basic testing of the functionality of hint
            assertNotNull(hint);
            assertTrue(all_moves.contains(hint));
            assertTrue(twoboard.validMove(hint));
            assertTrue(hint.toString() instanceof String);
            assertTrue(hint.toString().equals("Do this: ...."));

        }
        if (game.currentPlayer.makeMove(twoboard).equals(hint)){
//            Ensure that the same move cannot be hinted twice
            assertFalse(game.currentPlayer.getHint(twoboard).equals(hint));
        }

//        Ensure that the other player cannot have the same valid moves as the current player
        assertFalse(all_moves.containsAll(game.getNextPlayer().validMoves(twoboard)));
//        Ensure that the other player cannot have the same hint in its valid moves
        assertFalse(game.getNextPlayer().validMoves(twoboard).contains(hint));
    }
}
