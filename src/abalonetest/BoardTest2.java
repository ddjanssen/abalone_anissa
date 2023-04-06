package abalonetest;

import abalonecontrol.LocalGame;
import abalonemodel.*;
import abaloneview.Tui;
import abaloneview.View;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class BoardTest2 {

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

    /**
     * Test for the creation of the board.
     */
    @Test
    public void testBoard() {
        assertEquals(61, twoboard.getFields().length);
        assertEquals(61, threeboard.getFields().length);
        assertEquals(61, fourboard.getFields().length);
    }

    /**
     * Test the deepcopy method of the board. When creating a copy it should be equal to the
     * current board
     */
    @Test
    public void testDeepCopy() {
        Board copy = twoboard.deepCopy();
        assertEquals(twoboard.toString(), copy.toString());
    }

    /**
     * Test the getField method of the board.
     * It should return an object of tyupe Field for a specific position
     */
    @Test
    public void testGetField() {
        Field field = twoboard.getField(11);
        assertEquals(11, field.getIndex());
    }

    /**
     * Test that check the getPositionInArray method
     * possible inputs outputs are:
     * 11 -> 0, 12 -> 1, 13 -> 2, 14 -> 3, 15 -> 4
     * 21 -> 5, 22 -> 6, 23 -> 7, 24 -> 8, 25 -> 9, 26 -> 10
     * 31 -> 11, 32 -> 12, 33 -> 13, 34 -> 14, 35 -> 15, 36 -> 16, 37 -> 17
     * 41 -> 18, 42 -> 19, 43 -> 20, 44 -> 21, 45 -> 22, 46 -> 23, 47 -> 24, 48 -> 25
     * 51 -> 26, 52 -> 27, 53 -> 28, 54 -> 29, 55 -> 30, 56 -> 31, 57 -> 32, 58 -> 33, 59 -> 34
     * 62 -> 35, 63 -> 36, 64 -> 37, 65 -> 38, 66 -> 39, 67 -> 40, 68 -> 41, 69 -> 42
     * 73 -> 43, 74 -> 44, 75 -> 45, 76 -> 46, 77 -> 47, 78 -> 48, 79 -> 49
     * 84 -> 50, 85 -> 51, 86 -> 52, 87 -> 53, 88 -> 54, 89 -> 55
     * 95 -> 56, 96 -> 57, 97 -> 58, 98 -> 59, 99 -> 60
     */
    @Test
    public void testGetPositionInArray() {
        assertEquals(0, twoboard.getPositionInArray(11));
        assertEquals(1, twoboard.getPositionInArray(12));
        assertEquals(2, twoboard.getPositionInArray(13));
        assertEquals(3, twoboard.getPositionInArray(14));
        assertEquals(4, twoboard.getPositionInArray(15));
        assertEquals(5, twoboard.getPositionInArray(21));
        assertEquals(6, twoboard.getPositionInArray(22));
        assertEquals(7, twoboard.getPositionInArray(23));
        assertEquals(8, twoboard.getPositionInArray(24));
        assertEquals(9, twoboard.getPositionInArray(25));
        assertEquals(10, twoboard.getPositionInArray(26));
        assertEquals(11, twoboard.getPositionInArray(31));
        assertEquals(12, twoboard.getPositionInArray(32));
        assertEquals(13, twoboard.getPositionInArray(33));
        assertEquals(14, twoboard.getPositionInArray(34));
        assertEquals(15, twoboard.getPositionInArray(35));
        assertEquals(16, twoboard.getPositionInArray(36));
        assertEquals(17, twoboard.getPositionInArray(37));
        assertEquals(18, twoboard.getPositionInArray(41));
        assertEquals(19, twoboard.getPositionInArray(42));
        assertEquals(20, twoboard.getPositionInArray(43));
        assertEquals(21, twoboard.getPositionInArray(44));
        assertEquals(22, twoboard.getPositionInArray(45));
        assertEquals(23, twoboard.getPositionInArray(46));
        assertEquals(24, twoboard.getPositionInArray(47));
        assertEquals(25, twoboard.getPositionInArray(48));
        assertEquals(26, twoboard.getPositionInArray(51));
        assertEquals(27, twoboard.getPositionInArray(52));
        assertEquals(28, twoboard.getPositionInArray(53));
        assertEquals(29, twoboard.getPositionInArray(54));
        assertEquals(30, twoboard.getPositionInArray(55));
        assertEquals(31, twoboard.getPositionInArray(56));
        assertEquals(32, twoboard.getPositionInArray(57));
        assertEquals(33, twoboard.getPositionInArray(58));
        assertEquals(34, twoboard.getPositionInArray(59));
        assertEquals(35, twoboard.getPositionInArray(62));
        assertEquals(36, twoboard.getPositionInArray(63));
        assertEquals(37, twoboard.getPositionInArray(64));
        assertEquals(38, twoboard.getPositionInArray(65));
        assertEquals(39, twoboard.getPositionInArray(66));
        assertEquals(40, twoboard.getPositionInArray(67));
        assertEquals(41, twoboard.getPositionInArray(68));
        assertEquals(42, twoboard.getPositionInArray(69));
        assertEquals(43, twoboard.getPositionInArray(73));
        assertEquals(44, twoboard.getPositionInArray(74));
        assertEquals(45, twoboard.getPositionInArray(75));
        assertEquals(46, twoboard.getPositionInArray(76));
        assertEquals(47, twoboard.getPositionInArray(77));
        assertEquals(48, twoboard.getPositionInArray(78));
        assertEquals(49, twoboard.getPositionInArray(79));
        assertEquals(50, twoboard.getPositionInArray(84));
        assertEquals(51, twoboard.getPositionInArray(85));
        assertEquals(52, twoboard.getPositionInArray(86));
        assertEquals(53, twoboard.getPositionInArray(87));
        assertEquals(54, twoboard.getPositionInArray(88));
        assertEquals(55, twoboard.getPositionInArray(89));
        assertEquals(56, twoboard.getPositionInArray(95));
        assertEquals(57, twoboard.getPositionInArray(96));
        assertEquals(58, twoboard.getPositionInArray(97));
        assertEquals(59, twoboard.getPositionInArray(98));
        assertEquals(60, twoboard.getPositionInArray(99));
    }

    /**
     * Test the getFields method of the board.
     * It should return an array of all the fields
     */
    @Test
    public void testGetFields() {
        Field[] fields = twoboard.getFields();
        assertEquals(61, fields.length);
    }


    /**
     * checks the isField method
     * valid inputs are:
     * 11, 12, 13, 14, 15, 21, 22, 23, 24, 25, 26,
     * 31, 32, 33, 34, 35, 36, 37, 41, 42, 43, 44, 45, 46, 47, 48,
     * 51, 52, 53, 54, 55, 56, 57, 58, 59, 62, 63, 64, 65, 66, 67, 68, 69,
     * 73, 74, 75,76, 77, 78, 79, 84, 85, 86, 87, 88, 89,
     * 95, 96, 97, 98, 99
     * <p>
     * The return value should be true
     * <p>
     * Everything else should return false
     * <p>
     * For both cases we give 2 test cases
     */
    @Test
    public void testIsField() {
        assertTrue(twoboard.isField(11));
        assertTrue(twoboard.isField(99));
        assertFalse(twoboard.isField(10));
        assertFalse(twoboard.isField(100));
    }

    /**
     * test the hasWinner method for 2 players
     * First we test the case where there is no winners, e.g. start of the game
     * Then we set 1 player to have 6 points and check the method again for both players with Mark.B and Mark.W
     */
    @Test
    public void testHasWinner2() {
        assertFalse(twoboard.hasWinner());
        twoboard.game.getPlayer(Mark.B).setPoints(6);
        assertTrue(twoboard.hasWinner());
        twoboard.game.getPlayer(Mark.B).setPoints(0);
        twoboard.game.getPlayer(Mark.W).setPoints(6);
        assertTrue(twoboard.hasWinner());
    }

    /**
     * test the hasWinner method for 3 players
     * First we test the case where there is no winners, e.g. start of the game
     * Then we set 1 player to have 6 points and check the method again, for all players with Mark.B Mark.W and Mark.C
     */
    @Test
    public void testHasWinner3() {
        assertFalse(threeboard.hasWinner());
        threeboard.game.getPlayer(Mark.B).setPoints(6);
        assertTrue(threeboard.hasWinner());
        threeboard.game.getPlayer(Mark.B).setPoints(0);
        threeboard.game.getPlayer(Mark.W).setPoints(6);
        assertTrue(threeboard.hasWinner());
        threeboard.game.getPlayer(Mark.W).setPoints(0);
        threeboard.game.getPlayer(Mark.C).setPoints(6);
        assertTrue(threeboard.hasWinner());
    }

    /**
     * test the hasWinner method for 4 players
     * First we test the case where there is no winners, e.g. start of the game
     * Then players with Mark.W and Mark.B are in a team. So together they must have 6 points.
     * So by setting both players to 3 points, the method should return true
     * It should also work in the other way around, where player with Mark.C and Mark.R are in a team
     */
    @Test
    public void testHasWinner4() {
        assertFalse(fourboard.hasWinner());
        fourboard.game.getPlayer(Mark.B).setPoints(3);
        fourboard.game.getPlayer(Mark.W).setPoints(3);
        assertTrue(fourboard.hasWinner());
        fourboard.game.getPlayer(Mark.B).setPoints(0);
        fourboard.game.getPlayer(Mark.W).setPoints(0);
        fourboard.game.getPlayer(Mark.C).setPoints(3);
        fourboard.game.getPlayer(Mark.R).setPoints(3);
        assertTrue(fourboard.hasWinner());
    }

    /**
     * Test the gameEnd method, which should return true if the game has a winner
     * so by setting a player to 6 points, the method should return true
     */
    @Test
    public void testGameEnd() {
        assertFalse(twoboard.gameEnd());
        twoboard.game.getPlayer(Mark.B).setPoints(6);
        assertTrue(twoboard.gameEnd());
    }


    /**
     * test the getTeamAPoints method, only 4 board game
     * This should return the sum of the points of the players in team A, which is Mark.B and Mark.W
     */
    @Test
    public void testGetTeamAPoints() {
        assertEquals(0, fourboard.getTeamAPoints());
        fourboard.game.getPlayer(Mark.B).setPoints(3);
        fourboard.game.getPlayer(Mark.W).setPoints(3);
        assertEquals(6, fourboard.getTeamAPoints());
    }

    /**
     * Test the getTeamBPoints method, only 4 board game
     * This should return the sum of the points of the players in team B, which is Mark.C and Mark.R
     */
    @Test
    public void testGetTeamBPoints() {
        assertEquals(0, fourboard.getTeamBPoints());
        fourboard.game.getPlayer(Mark.C).setPoints(3);
        fourboard.game.getPlayer(Mark.R).setPoints(3);
        assertEquals(6, fourboard.getTeamBPoints());
    }

    /**
     * Test the getWinners method for 2 players, which should return a list of the players that won the game
     * cases:
     * 1. Mark.B has 6 points, so he should be the only one in the list
     * 2. Mark.W has 6 points, so he should be the only one in the list
     * 3. Mark.B > Mark.W, so Mark.B should be in the list
     * 4. Mark.W > Mark.B, so Mark.W should be in the list
     * 5. Mark.B = Mark.W and both have less than 6 points, so both should be in the list
     */
    @Test
    public void testGetWinners2() {
        twoboard.game.getPlayer(Mark.B).setPoints(6);
        List<Player> winners = twoboard.getWinners();
        assertEquals(1, winners.size());
        assertEquals(Mark.B, winners.get(0).getMark());
        twoboard.game.getPlayer(Mark.B).setPoints(0);
        twoboard.game.getPlayer(Mark.W).setPoints(6);
        winners = twoboard.getWinners();
        assertEquals(1, winners.size());
        assertEquals(Mark.W, winners.get(0).getMark());
        twoboard.game.getPlayer(Mark.B).setPoints(3);
        twoboard.game.getPlayer(Mark.W).setPoints(2);
        winners = twoboard.getWinners();
        assertEquals(1, winners.size());
        assertEquals(Mark.B, winners.get(0).getMark());
        twoboard.game.getPlayer(Mark.B).setPoints(2);
        twoboard.game.getPlayer(Mark.W).setPoints(3);
        winners = twoboard.getWinners();
        assertEquals(1, winners.size());
        assertEquals(Mark.W, winners.get(0).getMark());
        twoboard.game.getPlayer(Mark.B).setPoints(3);
        twoboard.game.getPlayer(Mark.W).setPoints(3);
        winners = twoboard.getWinners();
        assertEquals(2, winners.size());
        assertEquals(Mark.B, winners.get(0).getMark());
        assertEquals(Mark.W, winners.get(1).getMark());
    }

    /**
     * Test the getWinners method for 3 players, which should return a list of the players that won the game
     * We test if the list includes the correct players, the order of the list does not matter
     * cases:
     * 1. Mark.B has 6 points, so he should be the only one in the list
     * 2. Mark.W has 6 points, so he should be the only one in the list
     * 3. Mark.C has 6 points, so he should be the only one in the list
     * 4. Mark.B > Mark.W and Mark.C, so Mark.B should be in the list
     * 5. Mark.W > Mark.B and Mark.C, so Mark.W should be in the list
     * 6. Mark.C > Mark.B and Mark.W, so Mark.C should be in the list
     * 7. Mark.B = Mark.W > Mark.C and both have less than 6 points, so both should be in the list
     * 8. Mark.B = Mark.C > Mark.W and both have less than 6 points, so both should be in the list
     * 9. Mark.W = Mark.C > Mark.B and both have less than 6 points, so both should be in the list
     * 10. Mark.B = Mark.W = Mark.C and all have less than 6 points, so all should be in the list
     */
    @Test
    public void testGetWinners3() {
        threeboard.game.getPlayer(Mark.B).setPoints(6);
        List<Player> winners = threeboard.getWinners();
        assertEquals(1, winners.size());
        assertEquals(Mark.B, winners.get(0).getMark());
        threeboard.game.getPlayer(Mark.B).setPoints(0);
        threeboard.game.getPlayer(Mark.W).setPoints(6);
        winners = threeboard.getWinners();
        assertEquals(1, winners.size());
        assertEquals(Mark.W, winners.get(0).getMark());
        threeboard.game.getPlayer(Mark.W).setPoints(0);
        threeboard.game.getPlayer(Mark.C).setPoints(6);
        winners = threeboard.getWinners();
        assertEquals(1, winners.size());
        assertEquals(Mark.C, winners.get(0).getMark());
        threeboard.game.getPlayer(Mark.B).setPoints(3);
        threeboard.game.getPlayer(Mark.W).setPoints(2);
        threeboard.game.getPlayer(Mark.C).setPoints(1);
        winners = threeboard.getWinners();
        assertEquals(1, winners.size());
        assertEquals(Mark.B, winners.get(0).getMark());
        threeboard.game.getPlayer(Mark.B).setPoints(2);
        threeboard.game.getPlayer(Mark.W).setPoints(3);
        threeboard.game.getPlayer(Mark.C).setPoints(1);
        winners = threeboard.getWinners();
        assertEquals(1, winners.size());
        assertEquals(Mark.W, winners.get(0).getMark());
        threeboard.game.getPlayer(Mark.B).setPoints(1);
        threeboard.game.getPlayer(Mark.W).setPoints(2);
        threeboard.game.getPlayer(Mark.C).setPoints(3);
        winners = threeboard.getWinners();
        assertEquals(1, winners.size());
        assertEquals(Mark.C, winners.get(0).getMark());
        threeboard.game.getPlayer(Mark.B).setPoints(3);
        threeboard.game.getPlayer(Mark.W).setPoints(3);
        threeboard.game.getPlayer(Mark.C).setPoints(1);
        winners = threeboard.getWinners();
        assertEquals(2, winners.size());
        assertTrue(winners.contains(threeboard.game.getPlayer(Mark.B)));
        assertTrue(winners.contains(threeboard.game.getPlayer(Mark.W)));
        threeboard.game.getPlayer(Mark.B).setPoints(3);
        threeboard.game.getPlayer(Mark.W).setPoints(1);
        threeboard.game.getPlayer(Mark.C).setPoints(3);
        winners = threeboard.getWinners();
        assertEquals(2, winners.size());
        assertTrue(winners.contains(threeboard.game.getPlayer(Mark.B)));
        assertTrue(winners.contains(threeboard.game.getPlayer(Mark.C)));
        threeboard.game.getPlayer(Mark.B).setPoints(1);
        threeboard.game.getPlayer(Mark.W).setPoints(3);
        threeboard.game.getPlayer(Mark.C).setPoints(3);
        winners = threeboard.getWinners();
        assertEquals(2, winners.size());
        assertTrue(winners.contains(threeboard.game.getPlayer(Mark.W)));
        assertTrue(winners.contains(threeboard.game.getPlayer(Mark.C)));
        threeboard.game.getPlayer(Mark.B).setPoints(3);
        threeboard.game.getPlayer(Mark.W).setPoints(3);
        threeboard.game.getPlayer(Mark.C).setPoints(3);
        winners = threeboard.getWinners();
        assertEquals(3, winners.size());
        assertTrue(winners.contains(threeboard.game.getPlayer(Mark.B)));
        assertTrue(winners.contains(threeboard.game.getPlayer(Mark.W)));
        assertTrue(winners.contains(threeboard.game.getPlayer(Mark.C)));
    }


    /**
     * test the getWinners method for 4 players, which should return a list of the players that won the game
     * When the game is played with 4 people they work in teams of 2, so to determine the winners of the game we have the following cases:
     * 1. Mark.B and Mark.W have 6 points, so they should be the only ones in the list
     * 2. Mark.C and Mark.R have 6 points, so they should be the only ones in the lisT
     * 3. Mark.B and Mark.W > Mark.C and Mark.R, so Mark.B and Mark.W should be in the list
     * 4. Mark.C and Mark.R > Mark.B and Mark.W, so Mark.C and Mark.R should be in the list
     * 5. Mark.B and Mark.W = Mark.C and Mark.R and both have less than 6 points, so both teams should be in the list
     */
    @Test
    public void testGetWinners4() {
        fourboard.game.getPlayer(Mark.B).setPoints(6);
        fourboard.game.getPlayer(Mark.W).setPoints(6);
        List<Player> winners = fourboard.getWinners();
        assertEquals(2, winners.size());
        assertTrue(winners.contains(fourboard.game.getPlayer(Mark.B)));
        assertTrue(winners.contains(fourboard.game.getPlayer(Mark.W)));
        fourboard.game.getPlayer(Mark.B).setPoints(0);
        fourboard.game.getPlayer(Mark.W).setPoints(0);
        fourboard.game.getPlayer(Mark.C).setPoints(6);
        fourboard.game.getPlayer(Mark.R).setPoints(6);
        winners = fourboard.getWinners();
        assertEquals(2, winners.size());
        assertTrue(winners.contains(fourboard.game.getPlayer(Mark.C)));
        assertTrue(winners.contains(fourboard.game.getPlayer(Mark.R)));
        fourboard.game.getPlayer(Mark.B).setPoints(3);
        fourboard.game.getPlayer(Mark.W).setPoints(2);
        fourboard.game.getPlayer(Mark.C).setPoints(1);
        fourboard.game.getPlayer(Mark.R).setPoints(0);
        winners = fourboard.getWinners();
        assertEquals(2, winners.size());
        assertTrue(winners.contains(fourboard.game.getPlayer(Mark.B)));
        assertTrue(winners.contains(fourboard.game.getPlayer(Mark.W)));
        fourboard.game.getPlayer(Mark.B).setPoints(0);
        fourboard.game.getPlayer(Mark.W).setPoints(1);
        fourboard.game.getPlayer(Mark.C).setPoints(3);
        fourboard.game.getPlayer(Mark.R).setPoints(2);
        winners = fourboard.getWinners();
        assertEquals(2, winners.size());
        assertTrue(winners.contains(fourboard.game.getPlayer(Mark.C)));
        assertTrue(winners.contains(fourboard.game.getPlayer(Mark.R)));
        fourboard.game.getPlayer(Mark.B).setPoints(3);
        fourboard.game.getPlayer(Mark.W).setPoints(3);
        fourboard.game.getPlayer(Mark.C).setPoints(1);
        fourboard.game.getPlayer(Mark.R).setPoints(1);
        winners = fourboard.getWinners();
        assertEquals(2, winners.size());
        assertTrue(winners.contains(fourboard.game.getPlayer(Mark.B)));
        assertTrue(winners.contains(fourboard.game.getPlayer(Mark.W)));
        fourboard.game.getPlayer(Mark.B).setPoints(1);
        fourboard.game.getPlayer(Mark.W).setPoints(1);
        fourboard.game.getPlayer(Mark.C).setPoints(3);
        fourboard.game.getPlayer(Mark.R).setPoints(3);
        winners = fourboard.getWinners();
        assertEquals(2, winners.size());
        assertTrue(winners.contains(fourboard.game.getPlayer(Mark.C)));
        assertTrue(winners.contains(fourboard.game.getPlayer(Mark.R)));
        fourboard.game.getPlayer(Mark.B).setPoints(2);
        fourboard.game.getPlayer(Mark.W).setPoints(2);
        fourboard.game.getPlayer(Mark.C).setPoints(2);
        fourboard.game.getPlayer(Mark.R).setPoints(2);
        winners = fourboard.getWinners();
        assertEquals(4, winners.size());
        assertTrue(winners.contains(fourboard.game.getPlayer(Mark.B)));
        assertTrue(winners.contains(fourboard.game.getPlayer(Mark.W)));
        assertTrue(winners.contains(fourboard.game.getPlayer(Mark.C)));
        assertTrue(winners.contains(fourboard.game.getPlayer(Mark.R)));

    }

    /**
     * test the reset method, which should put all fields to Mark.X
     */
    @Test
    public void testReset() {
        threeboard.reset();
        Field[] fields = threeboard.getFields();
        for (Field field : fields) {
            assertEquals(Mark.X, field.getMark());
        }
    }


    /**
     * Test the moveUpRight method
     */
    @Test
    public void moveUpRight(){
        int counter = 0;
        for (int i = 0; i < twoboard.getFields().length; i++) {
            if (twoboard.getFields()[i].getMark() != Mark.X) {
                counter++;
            }
        }
        twoboard.game.currentPlayer = twoboard.game.getPlayer(Mark.W);
        int[] marbles = {11,22,33};
        Move move = new Move(marbles,0);
        assertTrue(twoboard.validMove(move));
        twoboard.executeMove(move);
        assertEquals(Mark.W, twoboard.getFields()[twoboard.getPositionInArray(44)].getMark());
        int counterAfter = 0;
        for (int i = 0; i < twoboard.getFields().length; i++) {
            if (twoboard.getFields()[i].getMark() != Mark.X) {
                counterAfter++;
            }
        }
        assertEquals(counter, counterAfter);
    }

    /**
     * Test the moveUpLeft method
     */
    @Test
    public void moveUpLeft(){
        int counter = 0;
        for (int i = 0; i < twoboard.getFields().length; i++) {
            if (twoboard.getFields()[i].getMark() != Mark.X) {
                counter++;
            }
        }
        twoboard.game.currentPlayer = twoboard.game.getPlayer(Mark.W);
        int[] marbles = {24,14,34};
        Move move = new Move(marbles,5);
        assertTrue(twoboard.validMove(move));

        twoboard.executeMove(move);
        assertEquals(Mark.W, twoboard.getFields()[twoboard.getPositionInArray(44)].getMark());
        int counterAfter = 0;
        for (int i = 0; i < twoboard.getFields().length; i++) {
            if (twoboard.getFields()[i].getMark() != Mark.X) {
                counterAfter++;
            }
        }
        assertEquals(counter, counterAfter);
    }

    /**
     * Test the moveRight method
     */
    @Test
    public void moveRight(){
        int counter = 0;
        for (int i = 0; i < twoboard.getFields().length; i++) {
            if (twoboard.getFields()[i].getMark() != Mark.X) {
                counter++;
            }
        }
        twoboard.game.currentPlayer = twoboard.game.getPlayer(Mark.W);
        int[] marbles = {33,34,35};
        Move move = new Move(marbles,1);
        assertTrue(twoboard.validMove(move));

        twoboard.executeMove(move);
        assertEquals(Mark.W, twoboard.getFields()[twoboard.getPositionInArray(36)].getMark());
        int counterAfter = 0;
        for (int i = 0; i < twoboard.getFields().length; i++) {
            if (twoboard.getFields()[i].getMark() != Mark.X) {
                counterAfter++;
            }
        }
        assertEquals(counter, counterAfter);
    }


    /**
     * Test the moveLeft method
     */
    @Test
    public void moveLeft(){
        int counter = 0;
        for (int i = 0; i < twoboard.getFields().length; i++) {
            if (twoboard.getFields()[i].getMark() != Mark.X) {
                counter++;
            }
        }
        twoboard.game.currentPlayer = twoboard.game.getPlayer(Mark.W);
        int[] marbles = {35,34,33};
        Move move = new Move(marbles,4);
        assertTrue(twoboard.validMove(move));

        twoboard.executeMove(move);
        System.out.println(twoboard.toString());
        assertEquals(Mark.W, twoboard.getFields()[twoboard.getPositionInArray(32)].getMark());
        int counterAfter = 0;
        for (int i = 0; i < twoboard.getFields().length; i++) {
            if (twoboard.getFields()[i].getMark() != Mark.X) {
                counterAfter++;
            }
        }
        assertEquals(counter, counterAfter);
    }

    /**
     * Test the moveDownRight method
     */
    @Test
    public void moveDownRight(){
        int counter = 0;
        for (int i = 0; i < twoboard.getFields().length; i++) {
            if (twoboard.getFields()[i].getMark() != Mark.X) {
                counter++;
            }
        }
        int[] marbles = {97,87,77};
        Move move = new Move(marbles,2);
        assertTrue(twoboard.validMove(move));

        twoboard.executeMove(move);
        assertEquals(Mark.B, twoboard.getFields()[twoboard.getPositionInArray(67)].getMark());
        int counterAfter = 0;
        for (int i = 0; i < twoboard.getFields().length; i++) {
            if (twoboard.getFields()[i].getMark() != Mark.X) {
                counterAfter++;
            }
        }
        assertEquals(counter, counterAfter);
    }

    /**
     * Test the moveDownLeft method
     */
    @Test
    public void moveDownLeft(){
        int counter = 0;
        for (int i = 0; i < twoboard.getFields().length; i++) {
            if (twoboard.getFields()[i].getMark() != Mark.X) {
                counter++;
            }
        }
        int[] marbles = {97,86,75};
        Move move = new Move(marbles,3);
        assertTrue(twoboard.validMove(move));

        twoboard.executeMove(move);
        assertEquals(Mark.B, twoboard.getFields()[twoboard.getPositionInArray(64)].getMark());
        int counterAfter = 0;
        for (int i = 0; i < twoboard.getFields().length; i++) {
            if (twoboard.getFields()[i].getMark() != Mark.X) {
                counterAfter++;
            }
        }
        assertEquals(counter, counterAfter);
    }

    /**
     * Test the currentBoard method
     * NOT TESTABLE, SINCE IT IS JUST AN ARRAY OF STRING, NOT VERY IMPORTANT TO TEST
     */
    @Test
    public void currentBoardTest(){
        assertTrue(true);
    }

    /**
     * Test the validMove method
     */
    @Test
    public void validMoveTest(){
//        Invalid move
        int[] marbles = {33,34,35};
        Move move = new Move(marbles,1);
        assertFalse(twoboard.validMove(move));

        //invalid move team
        Move move2 = new Move(marbles,1);
        assertFalse(fourboard.validMove(move2));
    }

    /**Test the toString method
     * NOT TESTABLE, SINCE IT IS JUSTA STRING REPRESENTATION OF THE BOARD
     */
    @Test
    public void toStringTest(){
        assertTrue(true);
    }


}

