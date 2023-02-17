package abalonemodel;

import abalonecontrol.Game;

import java.util.ArrayList;
import java.util.Random;


/**
 * A Class of a ComputerPlayer which extends a Player.
 * @author Anissa
 *
 */
public class ComputerPlayer extends Player {


    /**
     * Creates a new ComputerPlayer object.
     * 
     * @param string - the name
     * @param game   - the game which the player plays
     */
    public ComputerPlayer(String string, Game game) {
        super(string, game);
    }

    /**
     * for all fields on board. put the fields with this.mark in myFields
     */
    @Override
    public Move determineMove(Board board) {
        // TODO Auto-generated method stub
        //ArrayList<Move> moveOptions = new ArrayList<>();
        ArrayList<Field> myFields = new ArrayList<>();
        for (Field field : board.getFields()) {
            if (field.getMark() == getMark()) {
                myFields.add(field);
            }
            if (game.getNumberOfPlayers() == 4 && field.getMark() == getPartnerMark()) {
                myFields.add(field);
            }
        }
        // generate a random move
        int direction = getRandomInt(0, 5); // 6 options for direction
        Random random = new Random();
        int numberOfToBeMovedMarks = getRandomInt(1, 3);
        int[] indexOfToBeMovedMarks = new int[numberOfToBeMovedMarks];
        for (int i = 0; i < numberOfToBeMovedMarks; i++) {
            Field moving = myFields.get(random.nextInt(myFields.size()));
            indexOfToBeMovedMarks[i] = moving.getIndex();
        }
        return new Move(indexOfToBeMovedMarks, direction);
    }

    private int getRandomInt(double min, double max) {
        double x = (int) (Math.random() * ((max - min) + 1)) + min;
        return (int) x;

    }
}
