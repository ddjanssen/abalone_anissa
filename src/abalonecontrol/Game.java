package abalonecontrol;

import abalonemodel.Board;
import abalonemodel.ComputerPlayer;
import abalonemodel.HumanPlayer;
import abalonemodel.Mark;
import abalonemodel.Player;
import abaloneview.View;

/**
 * An abstract class for the <code>Game</code> thread.
 * 
 * @author Anissa
 *
 */
public abstract class Game extends Thread {

    public Player[] players;
    protected View view;
    protected Board board;
    private int numberOfPlayers;
    public Player currentPlayer;
    
    /**
     * Creates an instance of <code>Game</code>.
     * 
     * @param playernames   - a String array consisting the names of the players of the <code>Game</code>
     * @param view          - the <code>View</code> for the <code>Game</code>
     * 
     * @requires playernames != null && playernames.length >=2 && playernames.length <= 4
     * @requires view != null
     * 
     * @ensures board != new Board
     * @ensures all players have a mark
     */
    public Game(String[] playernames, View view) {
        this.view = view;
        board = new Board(this);
        // System.out.println(board);
        numberOfPlayers = playernames.length;
        players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) { // give mark to player
            Player player;
            if (playernames[i].contains("Computer")) {
                player = new ComputerPlayer(playernames[i], this);
            } else {
                player = new HumanPlayer(playernames[i], this);
            }
            players[i] = player;
        }
        setMarks();
        currentPlayer = players[0];
        setBoardSetUp();
    }



    /**
     * <code>setMark</code> gives each player their colour. 
     * fall through was meant to be
     * 
     * @ensures player.getmark != null
     */
    private void setMarks() {
        switch (numberOfPlayers) {
            case (4): {
                players[3].setMark(Mark.R);
            }
            case (3): {
                players[2].setMark(Mark.C);
            }
            case (2): {
                players[0].setMark(Mark.B);
                players[1].setMark(Mark.W);
                break;
            }
            default: { // no default needed since the values are already filtered menutui
            }
        }
    }

    /**
     * given a colour, it returns a player.
     * 
     * @return the player with the given mark
     * @requires mark != null
     * @ensures result != null
     */
    public Player getPlayer(Mark mark) {
        Player player = null;
        for (int i = 0; i < numberOfPlayers; i++) {
            if (players[i].getMark() == mark) {
                player = players[i];
            }
        }
        return player;

    }
    
    /**
     * returns the number of players of a game.
     * 
     * @return the number of players
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Returns the player whose turn it is.
     * 
     * @return <code>Player</code>
     */
    public Player getCurrentPlayer() {
        return currentPlayer;

    }
    
    /**
     * Returns the board of the current game.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * determines the next player by using <code>Mark</code>.
     * @return <code>Player</code>
     */
    public Player getNextPlayer() {
        switch (numberOfPlayers) {
            case (2): {
                return getPlayer(currentPlayer.getMark().getNextPlayerTwo());
            }
            case (3): {
                return getPlayer(currentPlayer.getMark().getNextPlayerThree());
            }
            case (4): {
                return getPlayer(currentPlayer.getMark().getNextPlayerFour());
            }
            default: {
                return null;
            }
        }
    }
    
    /**
     * Gets the <code>Player</code> with the name == argument.
     * @param name - name of the player
     * @return a player with the given name
     */
    public Player getPerson(String name) {
        for (int i = 0; i < numberOfPlayers; i++) {
            if (players[i].getName().equals(name)) {
                return players[i];
            }
        }
        return null;
    }

    

    /**
     * set all the marbles on the right position. this position is hardcoded since
     * this is neccessary. the array lenght is predefined since this is for all the
     * marbles of one game the same.
     * 
     * @ensures board != new Board
     */
    public void setBoardSetUp() {
        switch (numberOfPlayers) {
            case (2): {
                int[] positionBlack = { 75, 76, 77, 84, 85, 86, 87, 88, 89, 95, 96, 97, 98, 99 };
                int[] positionWhite = { 11, 12, 13, 14, 15, 21, 22, 23, 24, 25, 26, 33, 34, 35 };
                int arrayLength = 14;
                for (int i = 0; i < arrayLength; i++) {
                    board.getField(positionBlack[i]).setMark(Mark.B);
                    board.getField(positionWhite[i]).setMark(Mark.W);
                }
                break;
    
            }
            case (3): {
                int[] positionBlack = { 48, 58, 59, 68, 69, 78, 79, 88, 89, 98, 99 };
                int[] positionWhite = { 41, 51, 52, 62, 63, 73, 74, 84, 85, 95, 96 };
                int[] positionCyan = { 11, 12, 13, 14, 15, 21, 22, 23, 24, 25, 26 };
                int arrayLength = 11;
                for (int i = 0; i < arrayLength; i++) {
                    board.getField(positionBlack[i]).setMark(Mark.B);
                    board.getField(positionWhite[i]).setMark(Mark.W);
                    board.getField(positionCyan[i]).setMark(Mark.C);
                }
                break;
            }
            case (4): {
                int[] positionBlack = { 57, 58, 59, 67, 68, 69, 78, 79, 89 };
                int[] positionWhite = { 21, 31, 32, 41, 42, 43, 51, 52, 53 };
                int[] positionCyan = { 12, 13, 14, 15, 23, 24, 25, 34, 35 };
                int[] positionRed = { 75, 76, 85, 86, 87, 95, 96, 97, 98 };
                int arrayLength = 9;
                for (int i = 0; i < arrayLength; i++) {
                    board.getField(positionBlack[i]).setMark(Mark.B);
                    board.getField(positionWhite[i]).setMark(Mark.W);
                    board.getField(positionCyan[i]).setMark(Mark.C);
                    board.getField(positionRed[i]).setMark(Mark.R);
                    
                }
                break;
            }
            default:
                break;
        }
    }

    /**
     * Starts the Abalone game. 
     * 
     * @ensures game starts with reset settings
     */
    @Override
    public void run() {
        view.printBoard(board);
        play();
    }

    /**
     * This method resets the <code>Game</code>.
     */
    public void reset() {
        board.reset();

    }

    /**
     * Play an instance of <code>Game</code>.
     */
    protected abstract void play();

    /**
     * resets the points of each player.
     */
    public void resetPlayerPoints() {
        for (Player p : players) {
            p.setPoints(0);
        }
        
    }

}
