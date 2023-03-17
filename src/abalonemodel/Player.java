package abalonemodel;

import abalonecontrol.Game;

import java.util.ArrayList;

/**
 * Abstract class of a Player in Abalone.
 * 
 * @author Anissa
 *
 */
public abstract class Player {

    private String name;
    protected Mark mark;
    protected Game game;
    private int points;

    /**
     * Creates a new Player object.
     * 
     * @requires name != null
     * @requires mark != null
     * @param name - the name of the player
     * @param game - the game which the player plays
     */

    public Player(String name, Game game) {
        // TODO Auto-generated constructor stub
        this.name = name;
        this.game = game;
        this.mark = null;
        this.points = 0;

    }

    public ArrayList<Move> validMoves(Board board) {
        return new ArrayList<>();
    }

    public Move getHint(Board board) {
        return this.validMoves(board).get(getRandomInt(0, this.validMoves(board).size()-1));
    }
    
    public int getPoints() {
        return points;
    }
    
    /**
     * used for reset.
     * @param points - the number of points that the player suppose to have
     */
    public void setPoints(int points) {
        this.points = points;
    }
    
    /**
     * increment points when sumito happens.
     */
    public void upPoints() {
        this.points++;
    }

    /**
     * Returns the name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the mark of the player.
     */
    public Mark getMark() {
        return mark;
    }

    /**
     * Sets the Mark of the player.
     * 
     * @requires mark != null
     */
    public void setMark(Mark mark) {
        this.mark = mark;
    }
    
    /**
     * returns the mark of the teamplayer when there is a 4 player game.
     */
    public Mark getPartnerMark() {
        switch (this.mark) {
            case B: {
                return Mark.W; 
            }
            case W: {
                return Mark.B;
            }
            case C: {
                return Mark.R;
            }
            case R: {
                return Mark.C;
            }
            default: {
                return null;
            }
        }
        
    }

    /**
     * Determines the field for the next move.
     * 
     * @requires board is not null and not full
     * @ensures the returned in is a valid field index and that field is empty
     * @param board the current game board
     * @return the player's choice
     */
    public abstract Move determineMove(Board board);

    /**
     * Makes a move on the board.
     * 
     * @requires board != null
     * @requires board != full
     * @param board - the current board
     */
    public Move makeMove(Board board) {
        Move choice = null;
        boolean makingMove = true;
        while (makingMove) {
            choice = determineMove(board);
            if (board.validMove(choice)) {
                makingMove = false;                
            }
        }
        return choice;
        // to be implemented board.move(choice, getMark())
    }

    private int getRandomInt(double min, double max) {
        double x = (int) (Math.random() * ((max - min) + 1)) + min;
        return (int) x;

    }
}
