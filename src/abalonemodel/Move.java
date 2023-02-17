package abalonemodel;

public class Move {

    private int[] marblePositions;
    private int direction;

    /**
     *initialises the board on which the move happens and the mark .
     */

    public Move(int[] marblePositions, int direction) {
        this.marblePositions = marblePositions;
        this.direction = direction;
    }

    public int[] getMarblePositions() {
        return marblePositions;
    }

    public int getDirection() {
        return direction;
    }






}
