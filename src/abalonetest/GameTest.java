package abalonetest;

import abalonecontrol.Game;
import abalonecontrol.LocalGame;
import abalonemodel.Mark;
import abaloneview.Tui;
import abaloneview.View;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest {
    View view = new Tui();
    Game gameTwoPlayer;
    Game gameThreePlayer;
    Game gameFourPlayer;
    @Before
    public void setUp(){
        gameTwoPlayer = new LocalGame(new String[]{"a","b"}, view);
        gameThreePlayer = new LocalGame(new String[]{"a","b","c"}, view);
        gameFourPlayer = new LocalGame(new String[]{"a","b","c","d"}, view);

    }

    @Test
    public void testConstructor(){
        Game gameOnePlayer = new LocalGame(new String[]{"a"}, view);
        assertEquals(gameOnePlayer.getNumberOfPlayers(),1);
        Game gameOneComputer = new LocalGame(new String[]{"Computer"}, view);
        assertEquals(gameOneComputer.getNumberOfPlayers(),1);

        assertEquals(gameOnePlayer.getCurrentPlayer(), gameOnePlayer.getPerson("a"));
    }

    @Test
    public void testMarks(){
        assertEquals(gameTwoPlayer.getPerson("a").getMark(), Mark.B);
        assertEquals(gameTwoPlayer.getPerson("b").getMark(), Mark.W);

        assertEquals(gameThreePlayer.getPerson("a").getMark(), Mark.B);
        assertEquals(gameThreePlayer.getPerson("b").getMark(), Mark.W);
        assertEquals(gameThreePlayer.getPerson("c").getMark(), Mark.C);

        assertEquals(gameFourPlayer.getPerson("a").getMark(), Mark.B);
        assertEquals(gameFourPlayer.getPerson("b").getMark(), Mark.W);
        assertEquals(gameFourPlayer.getPerson("c").getMark(), Mark.C);
        assertEquals(gameFourPlayer.getPerson("d").getMark(), Mark.R);

        assertEquals(gameFourPlayer.getPlayer(Mark.B), gameFourPlayer.getPerson("a"));
        assertEquals(gameFourPlayer.getPlayer(Mark.W), gameFourPlayer.getPerson("b"));
        assertEquals(gameFourPlayer.getPlayer(Mark.C), gameFourPlayer.getPerson("c"));
        assertEquals(gameFourPlayer.getPlayer(Mark.R), gameFourPlayer.getPerson("d"));
    }

    @Test
    public void testNoOfPlayer(){
        assertEquals(gameTwoPlayer.getNumberOfPlayers(), 2);
        assertEquals(gameThreePlayer.getNumberOfPlayers(), 3);
        assertEquals(gameFourPlayer.getNumberOfPlayers(), 4);
    }

    @Test
    public void testNextPlayer(){
        assertEquals(gameTwoPlayer.getNextPlayer(), gameTwoPlayer.getPerson("b"));
        assertEquals(gameThreePlayer.getNextPlayer(), gameThreePlayer.getPerson("c"));
        assertEquals(gameFourPlayer.getNextPlayer(), gameFourPlayer.getPerson("c"));

    }


}
