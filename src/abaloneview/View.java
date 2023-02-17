package abaloneview;

import abalonecontrol.Game;
import abalonecontrol.LocalGame;
import abalonemodel.Board;
import abalonemodel.Player;

import java.util.Scanner;

/**
 *  Abstract class of the View.
 * @author Anissa
 *
 */
public abstract class View /* implements Observer */ {
    private Game game;
    Scanner input = new Scanner(System.in);

    /**
     * This creates an instance of <code>View</code>. If [GROENE DEEL WORDT
     * MEEGENOMEN] the game is a local game then there is a new localgame created.
     * 
     * @param playerNames - the names of the players
     * @param game        -
     */
    protected View(String[] playerNames, Class<LocalGame> game) {
        this.game = new LocalGame(playerNames, this);
    }
    
    protected View() {
        
    }

    /**
     * this method starts the game thread.
     */
    public void start() {
        game.start();
    }

    public abstract boolean askToContinuePlaying();

    public abstract void printBoard(Board board);

    public abstract void showWinners(Board board, Player[] players);

}
