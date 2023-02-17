package abalonecontrol;

import abalonemodel.Move;
import abalonemodel.Player;
import abalonetest.abaloneexceptions.IllegalMoveException;
import abaloneview.ClientTui;


/**
 * The <code>ClientGame</code> is used for a networked game.
 * 
 * @author Anissa
 */
public class ClientGame extends LocalGame {
    /**
     * Creates a new instance of <code>ClientGame</code>.
     * 
     * @param playernames   - the names of all the <code>Players</code> of the game
     * @param view          - the user interface
     */
    public ClientGame(String[] playernames, ClientTui view) {
        super(playernames, view);
    }
    
    
    /**
     * Executes the move of the other networked player.
     * 
     * @param move - received, to be executed move of current networked player
     * @throws IllegalMoveException - received move is invalid
     */
    public void doMove(Move move) throws IllegalMoveException {
        if (board.validMove(move)) {
            board.executeMove(move);
        } else {
            throw new IllegalMoveException("ERROR: Move is invalid");
            
        }
    }


    public void setCurrentPlayer(Player nextPlayer) {
        currentPlayer = nextPlayer;
    }
}
