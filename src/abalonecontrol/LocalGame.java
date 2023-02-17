package abalonecontrol;

import abalonemodel.Move;
import abaloneview.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class <code>LocalGame</code> extends <code>Game</code>.
 * @author Anissa
 *
 */
public class LocalGame extends Game {
    private static final String[] NAIVEBOARDINDEX = { 
        "57", "58", "59", "60", "61",
        "51", "52", "53", "54", "55", "56", 
        "44", "45", "46", "47", "48", "49", "50",
        "36", "37", "38", "39", "40", "41", "42", "43",
        "27", "28", "29", "30", "31", "32", "33", "34", "35",
        "19", "20", "21", "22", "23", "24", "25", "26",
        "12", "13", "14", "15", "16", "17", "18",
        "06", "07", "08", "09", "10", "11",
        "01", "02", "03", "04", "05"
        };
    Map<String, Integer> naiveIndex = new HashMap<>();
    Map<Integer, String> indexNaive = new HashMap<>();
        
    /**
     * Creates an instance of a <code>Localgame</code>.
     * 
     * @param playernames - String array with the names of the players
     * @param view - the <code>View</code> for the <code>Game</code>
     * 
     * @requires playernames.lenght >= 2 && playernames.lenght <= 4
     * @requires view != null
     */
    public LocalGame(String[] playernames, View view) {
        super(playernames, view);     
        for (int i = 0; i < NAIVEBOARDINDEX.length; i++) {
            naiveIndex.put(NAIVEBOARDINDEX[i], getBoard().index[i]);
            indexNaive.put(getBoard().index[i], NAIVEBOARDINDEX[i]);
        }
    }
    
    /** {11, 12} -> 575800
     * converting the local index of the marble to the server naive index.
     * @param marbles - int values of the marbles on local board
     */
    public String convertIndexToNaive(int[] marbles) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            if (i > (marbles.length - 1)) {           // if there are less than 3 marbles, fill with 00
                result.append("00");
            } else {
                result.append(indexNaive.get(marbles[i]));
            }
        }
        return result.toString();
    }
    
    /** 010200 -> {95, 96} 
     * converting the server marble position to local position index.
     * @param marbles - string values of the marbles on naive board
     */
    public int[] convertNaiveToIndex(String marbles) {
        String[] marblesNaive = marbles.split("(?<=\\G..)");
        List<String> temp = new ArrayList<String>();        //list because size not known
        for (int i = 0; i < marblesNaive.length; i++) {
            if (!marblesNaive[i].equals("00")) {            // 00 means no marble. so marbles < 3
                temp.add(marblesNaive[i]);
            }
        }
        int[] result = new int[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            result[i] = naiveIndex.get(temp.get(i));
        }
        return result;
    }
    
    /**
     * With this method you play the game.
     * while game not over, you make a move, next player
     */
    @Override
    protected void play() {
        boolean wantToPlay = true;
        while (wantToPlay) {
            int turns = 0;
            while (!board.hasWinner() && turns < 96) {
                Move move = currentPlayer.makeMove(board);
                board.executeMove(move);
                view.printBoard(board);
                currentPlayer = getNextPlayer();
                turns++;
            }
            view.showWinners(board, players);   // if turns = 96 draw
            wantToPlay = view.askToContinuePlaying();
            if (wantToPlay) {
                board.reset();
                currentPlayer = players[0];
                setBoardSetUp();
            }
        }
    }

}
