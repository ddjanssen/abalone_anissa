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


public class MoveTest {

    @Test
    public void isField() {
        for( int i = 0; i < 20; i++ ) {
            //random int betwwn 0 and 6
            int random = (int)(Math.random() * 7);
            int randomRange = (int)(Math.random() * 3);
            int[] marbles = new int[randomRange];
            for( int j = 0; j < randomRange; j++ ) {
                marbles[j] = (int)(Math.random() * 94);
            }
            Move move = new Move( marbles, random );
            assertEquals( random, move.getDirection() );
            assertEquals( marbles, move.getMarblePositions());
        }
    }

}