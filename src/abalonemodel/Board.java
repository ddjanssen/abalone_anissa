package abalonemodel;

import abalonecontrol.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Class <code>Board</code> is the board for each game.
 * 
 * @author Anissa
 */
public class Board {

    private static final ArrayList<String> NUMBERING = new ArrayList<String>(
            Arrays.asList(
                    "----------- 95 | 96 | 97 | 98 | 99 -----------",
                    "-------- 84 | 85 | 86 | 87 | 88 | 89 --------",
                    "----- 73 | 74 | 75 | 76 | 77 | 78 | 79 -----",
                    "--- 62 | 63 | 64 | 65 | 66 | 67 | 68 | 69 ---", 
                    " 51 | 52 | 53 | 54 | 55 | 56 | 57 | 58 | 59 ",
                    "--- 41 | 42 | 43 | 44 | 45 | 46 | 47 | 48 ---",
                    "----- 31 | 32 | 33 | 34 | 35 | 36 | 37 -----",
                    "------- 21 | 22 | 23 | 24 | 25 | 26 -------", 
                    "----------- 11 | 12 | 13 | 14 | 15 -----------"));
    private Field[] fields;
    public final int[] index = { 
        11, 12, 13, 14, 15, 
        21, 22, 23, 24, 25, 26,
        31, 32, 33, 34, 35, 36, 37, 
        41, 42, 43, 44, 45, 46, 47, 48, 
        51, 52, 53, 54, 55, 56, 57, 58, 59, 
        62, 63, 64, 65, 66, 67, 68, 69, 
        73, 74, 75,76, 77, 78, 79,
        84, 85, 86, 87, 88, 89, 
        95, 96, 97, 98, 99 
        };
    public Game game;
    private ArrayList<Player> teamA;
    private ArrayList<Player> teamB;
    private Map<Integer, Integer> indexToPosition = new HashMap<>();
    private ArrayList<String> currentBoard;

    /**
     * Creates a new instance of <code>Board</code>.
     * @param game - the game of this board
     */
    public Board(Game game) {
        this.game = game;
        fields = new Field[61];
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i] = new Field(index[i], Mark.X);
                indexToPosition.put(index[i], i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * creates a deep copy of the board.
     *
     * @ensures the result is a new board.
     * @ensures the values of all the fields on the new board are the same as the
     *          values of the fields on this board.
     * @return the copy of the board.
     */
    public Board deepCopy() {
        Board copy = new Board(game);
        for (int i = 0; i < fields.length; i++) {
            copy.fields[i] = this.fields[i];
        }
        return copy;
    }

    /**
     * This method might not be needed. Gives the index for the field.
     * 
     * @requires 0 < row && row <= NUMBERING.length
     * @requires 0 < dia && dia <= NUMBERING.length //@param field -the field of
     *           which you want to get the index.
     * @return the index beloning to the (row,col)- field.
     */
    public Field getField(int index) {
        return fields[getPositionInArray(index)];
    }

    /**
     * maps the index with the position in the field[]. for example index 11 is
     * fields[0]
     * 
     * @param index - the value(index) of a field
     * @return - the position of the field in fields[]
     */
    public int getPositionInArray(int index) {
        return indexToPosition.get(index);
    }

    /**
     * returns all the fields of the board.
     */
    public Field[] getFields() {
        return fields;
    }

    /**
     * Checks whether a given index is a field of the board.
     * @param index - the value(index) of a field
     */
    public boolean isField(int index) {
        for (int i : this.index) {
            if (i == index) {
                return true;
            }
        }
        return false;
    }

    /**
     * indicates if there is a winner.
     * 
     * @return true if (one of the) other player(s) have lost 6 marbles.
     */
    public boolean hasWinner() {
        int nrOfPlayers = game.getNumberOfPlayers();
        switch (nrOfPlayers) {
            case (2): { //14 marbles per person
                if (game.getPlayer(Mark.B).getPoints() == 6 || game.getPlayer(Mark.W).getPoints() == 6) {
                    return true;
                }
                break;
            }
            case (3): { // 11 marbles per person
                if (game.getPlayer(Mark.B).getPoints() == 6 || game.getPlayer(Mark.W).getPoints() == 6
                        || game.getPlayer(Mark.C).getPoints() == 6) {
                    return true;
                }
                break;
            }
            case (4): { // 9 marbles per person
                if (getTeamAPoints() == 6 || getTeamBPoints() == 6) {
                    return true;
                }
                break;
            }
            default: {
            }
        }
        return false;
    }
    
    

    /**
     * game ends if there is a winner or after 96 turns there still is no winner.
     * 
     * @return true if the game has ended
     */
    public boolean gameEnd() {
        return hasWinner();
    }

    /**
     * returns a ArrayList of the players in teamA.
     * the players with mark black and white
     */
    private ArrayList<Player> getTeamA() {
        teamA = new ArrayList<>();
        teamA.add(game.getPlayer(Mark.B));
        teamA.add(game.getPlayer(Mark.W));
        return teamA;
    }

    /**
     * returns a ArrayList of the players in teamB.
     * the players with the mark cyan and red
     * @return
     */
    private ArrayList<Player> getTeamB() {
        teamB = new ArrayList<>();
        teamB.add(game.getPlayer(Mark.C));
        teamB.add(game.getPlayer(Mark.R));
        return teamB;
    }

    /**
     * Returns the points of team A.
     */
    public int getTeamAPoints() {
        int pointsA = 0;
        for (Player player : getTeamA()) {
            pointsA = pointsA + player.getPoints();
        }
        return pointsA;
    }

    /**
     * returns the points of team B.
     */
    public int getTeamBPoints() {
        int pointsB = 0;
        for (Player player : getTeamB()) {
            pointsB = pointsB + player.getPoints();
        }
        return pointsB;
    }

    /**
     * returns the winners of the game.
     * @return arraylist consistion
     */
    public ArrayList<Player> getWinners() {
        ArrayList<Player> winners = new ArrayList<>();
        int nrOfPlayers = game.getNumberOfPlayers();
        switch (nrOfPlayers) {
            case (2): {
                if (game.getPlayer(Mark.B).getPoints() == 6) {
                    winners.add(game.getPlayer(Mark.B));
                } else if (game.getPlayer(Mark.W).getPoints() == 6) {
                    winners.add(game.getPlayer(Mark.W));
                } else if (game.getPlayer(Mark.B).getPoints() > game.getPlayer(Mark.W).getPoints()) {
                    winners.add(game.getPlayer(Mark.B));
                } else if (game.getPlayer(Mark.W).getPoints() > game.getPlayer(Mark.B).getPoints()) {
                    winners.add(game.getPlayer(Mark.W));
                } else {
                    winners.add(game.getPlayer(Mark.B));
                    winners.add(game.getPlayer(Mark.W));
                }
                break;
            }
            case (3): {
                if (game.getPlayer(Mark.B).getPoints() == 6) {
                    winners.add(game.getPlayer(Mark.B));
                } else if (game.getPlayer(Mark.W).getPoints() == 6) {
                    winners.add(game.getPlayer(Mark.W));
                } else if (game.getPlayer(Mark.C).getPoints() == 6) {
                    winners.add(game.getPlayer(Mark.C));
                } else if (game.getPlayer(Mark.B).getPoints() > game.getPlayer(Mark.W).getPoints()) {
                    if (game.getPlayer(Mark.B).getPoints() > game.getPlayer(Mark.C).getPoints()) {
                        winners.add(game.getPlayer(Mark.B));
                    } else if (game.getPlayer(Mark.B).getPoints() == game.getPlayer(Mark.C).getPoints()) {
                        winners.add(game.getPlayer(Mark.B));
                        winners.add(game.getPlayer(Mark.C));
                    } else {
                        winners.add(game.getPlayer(Mark.C));

                    }
                } else if (game.getPlayer(Mark.B).getPoints() > game.getPlayer(Mark.C).getPoints()) {
                    if (game.getPlayer(Mark.B).getPoints() > game.getPlayer(Mark.W).getPoints()) {
                        winners.add(game.getPlayer(Mark.B));
                    } else if (game.getPlayer(Mark.B).getPoints() == game.getPlayer(Mark.W).getPoints()) {
                        winners.add(game.getPlayer(Mark.B));
                        winners.add(game.getPlayer(Mark.W));
                    } else {
                        winners.add(game.getPlayer(Mark.W));

                    }
                } else if (game.getPlayer(Mark.B).getPoints() == game.getPlayer(Mark.W).getPoints()) {
                    if (game.getPlayer(Mark.B).getPoints() < game.getPlayer(Mark.C).getPoints()) {
                        winners.add(game.getPlayer(Mark.C));
                    } else if (game.getPlayer(Mark.B).getPoints() == game.getPlayer(Mark.C).getPoints()) {
                        winners.add(game.getPlayer(Mark.B));
                        winners.add(game.getPlayer(Mark.W));
                        winners.add(game.getPlayer(Mark.C));
                    }
                } else if (game.getPlayer(Mark.B).getPoints() < game.getPlayer(Mark.C).getPoints()) {
                    if (game.getPlayer(Mark.C).getPoints() > game.getPlayer(Mark.W).getPoints()) {
                        winners.add(game.getPlayer(Mark.C));
                    } else if (game.getPlayer(Mark.C).getPoints() < game.getPlayer(Mark.W).getPoints()) {
                        winners.add(game.getPlayer(Mark.W));
                    } else {
                        winners.add(game.getPlayer(Mark.C));
                        winners.add(game.getPlayer(Mark.W));
                    }
                } else if (game.getPlayer(Mark.B).getPoints() < game.getPlayer(Mark.W).getPoints()) {
                    if (game.getPlayer(Mark.C).getPoints() > game.getPlayer(Mark.W).getPoints()) {
                        winners.add(game.getPlayer(Mark.C));
                    } else if (game.getPlayer(Mark.C).getPoints() < game.getPlayer(Mark.W).getPoints()) {
                        winners.add(game.getPlayer(Mark.W));
                    } else {
                        winners.add(game.getPlayer(Mark.C));
                        winners.add(game.getPlayer(Mark.W));
                    }
                }
                   
                break;
                
            }
            
            case (4): {
                if (getTeamAPoints() == 6) {
                    winners.add(game.getPlayer(Mark.B));
                    winners.add(game.getPlayer(Mark.W));
                } else if (getTeamBPoints() == 6) {
                    winners.add(game.getPlayer(Mark.C));
                    winners.add(game.getPlayer(Mark.R));
                } else if (getTeamAPoints() > getTeamBPoints()) {
                    winners.add(game.getPlayer(Mark.B));
                    winners.add(game.getPlayer(Mark.W));
                } else if (getTeamBPoints() > getTeamAPoints()) {
                    winners.add(game.getPlayer(Mark.C));
                    winners.add(game.getPlayer(Mark.R));
                } else {
                    winners.add(game.getPlayer(Mark.B));
                    winners.add(game.getPlayer(Mark.W));
                    winners.add(game.getPlayer(Mark.C));
                    winners.add(game.getPlayer(Mark.R));
                }
                break;
            }
            default: {
            }
        }
        return winners;
    }


    /**
     * Resets the board so it is empty again.
     * 
     * @ensures the board is empty.
     */
    public void reset() {
        for (int i = 0; i < index.length; i++) {
            try {
                fields[i].setMark(Mark.X);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        game.resetPlayerPoints();
    }

    /**
     * checks if there is a predecessor of a marble in a certain position.
     * used when the marbles are moved. 
     * 
     * @param fields - the positions of the marbles after moved
     * @param index  - is a 'old' position of a marble
     * @return
     */
    private Boolean fieldInArray(int[] fields, int index) {
        for (int i : fields) {
            if (i == index) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * pushes a marble from opponent from the board.
     * 
     * @param opponentPosition - opponents marble directly in front of players marble
     * @param directionvalue - the direction of the push
     */
    private void sumito(int opponentPosition, int directionvalue) {
        boolean moveablemarble = true;
        while (moveablemarble) {
            if (!isField(opponentPosition + directionvalue)) {
                moveablemarble = false;
                if (getField(opponentPosition - directionvalue).getMark() !=  Mark.X) {
                    getField(opponentPosition).setMark(Mark.X);
                }
                
            } else {
                Mark mark = getField(opponentPosition).getMark();
                if (getField(opponentPosition - directionvalue).getMark() !=  Mark.X) {
                    getField(opponentPosition).setMark(Mark.X);
                }
                if (getField(opponentPosition + directionvalue).getMark() == Mark.X) {
                    moveablemarble = false;
                }
                getField(opponentPosition + directionvalue).setMark(mark);
                opponentPosition += directionvalue;
            }
        }
    }

    /**
     * moves the mark up to the right.
     * 
     * @param move - the move has the information with what you want to move in what
     *             direction.
     * @requires the move is valid.
     * @requires fields are valid
     * @ensures the field right above has currentplayer.getmark.
     */
    public void moveUpRight(Move move) {
        int[] fields = move.getMarblePositions();
        for (int i = 0; i < fields.length; i++) {
            if (getField(fields[i] + 11).getMark() != game.getCurrentPlayer().getMark() 
                    && getField(fields[i] + 11).getMark() != Mark.X) {
                if (game.getNumberOfPlayers() == 4) {
                    if (getField(fields[i] + 11).getMark() 
                            != game.getCurrentPlayer().getPartnerMark()) {                   
                        int opponentPosition = fields[i] + 11;
                        sumito(opponentPosition, 11);
                    }
                } else {
                    int opponentPosition = fields[i] + 11;
                    sumito(opponentPosition, 11);
                }
            }
            getField(fields[i] + 11).setMark(getField(fields[i]).getMark());
            if (!fieldInArray(fields, fields[i] - 11)) {
                getField(fields[i]).setMark(Mark.X);
            }
        }
    }

    /**
     * moves the mark up to the left.
     * 
     * @param move - the move has the information with what you want to move in what
     *             direction.
     * @requires the move is valid.
     * @requires fields are valid.
     * @ensures the field left above has currentplayer.getmark.
     */
    public void moveUpLeft(Move move) {
        int[] fields = move.getMarblePositions();
        for (int i = 0; i < fields.length; i++) {
            if (getField(fields[i] + 10).getMark() != game.getCurrentPlayer().getMark() 
                    && getField(fields[i] + 10).getMark() != Mark.X) {
                if (game.getNumberOfPlayers() == 4) {
                    if (getField(fields[i] + 10).getMark() 
                            != game.getCurrentPlayer().getPartnerMark()) {                   
                        int opponentPosition = fields[i] + 10;
                        sumito(opponentPosition, 10);
                    }
                } else {
                    int opponentPosition = fields[i] + 10;
                    sumito(opponentPosition, 10);
                }
            }
            getField(fields[i] + 10).setMark(getField(fields[i]).getMark());
            if (!fieldInArray(fields, fields[i] - 10)) {
                getField(fields[i]).setMark(Mark.X);
            }
        }
    }

    /**
     * moves the mark to the right.
     * 
     * @param move - the move has the information with what you want to move in what
     *             direction.
     * @requires the move is valid.
     * @requires fields are valid.
     * @ensures the field to the right has currentplayer.getmark.
     */
    public void moveRight(Move move) {
        int[] fields = move.getMarblePositions();
        for (int i = 0; i < fields.length; i++) {
            if (getField(fields[i] + 1).getMark() != game.getCurrentPlayer().getMark() 
                    && getField(fields[i] + 1).getMark() != Mark.X) {
                if (game.getNumberOfPlayers() == 4) {
                    if (getField(fields[i] + 1).getMark() 
                            != game.getCurrentPlayer().getPartnerMark()) {                   
                        int opponentPosition = fields[i] + 1;
                        sumito(opponentPosition, 1);
                    }
                } else {
                    int opponentPosition = fields[i] + 1;
                    sumito(opponentPosition, 1);
                }
            }
            getField(fields[i] + 1).setMark(getField(fields[i]).getMark());
            if (!fieldInArray(fields, fields[i] - 1)) {
                getField(fields[i]).setMark(Mark.X);
            }
        }
    }

    /**
     * moves the mark to the left.
     * 
     * @param move - the move has the information with what you want to move in what
     *             direction.
     * @requires the move is valid.
     * @requires fields are valid.
     * @ensures the field to the left has currentplayer.getmark.
     */
    public void moveLeft(Move move) {
        int[] fields = move.getMarblePositions();
        for (int i = 0; i < fields.length; i++) {
            if (getField(fields[i] - 1).getMark() != game.getCurrentPlayer().getMark() 
                    && getField(fields[i] - 1).getMark() != Mark.X) {
                if (game.getNumberOfPlayers() == 4) {
                    if (getField(fields[i] - 1).getMark() 
                            != game.getCurrentPlayer().getPartnerMark()) {                   
                        int opponentPosition = fields[i] - 1;
                        sumito(opponentPosition, -1);
                    }
                } else {
                    int opponentPosition = fields[i] - 1;
                    sumito(opponentPosition, -1);
                }
            }
            getField(fields[i] - 1).setMark(getField(fields[i]).getMark());
            if (!fieldInArray(fields, fields[i] + 1)) {
                getField(fields[i]).setMark(Mark.X);
            }
        }
    }

    /**
     * moves the mark down to the right.
     * 
     * @param move - the move has the information with what you want to move in what
     *             direction.
     * @requires the move is valid.
     * @requires field is a valid field.
     * @ensures the field right beneath has currentplayer.getmark.
     */
    public void moveDownRight(Move move) {
        int[] fields = move.getMarblePositions();
        for (int i = 0; i < fields.length; i++) {
            if (getField(fields[i] - 10).getMark() != game.getCurrentPlayer().getMark() 
                    && getField(fields[i] - 10).getMark() != Mark.X) {
                if (game.getNumberOfPlayers() == 4) {
                    if (getField(fields[i] - 10).getMark() 
                            != game.getCurrentPlayer().getPartnerMark()) {                   
                        int opponentPosition = fields[i] - 10;
                        sumito(opponentPosition, -10);
                    }
                } else {
                    int opponentPosition = fields[i] - 10;
                    sumito(opponentPosition, -10);
                }
            }
            getField(fields[i] - 10).setMark(getField(fields[i]).getMark());
            if (!fieldInArray(fields, fields[i] + 10)) {
                getField(fields[i]).setMark(Mark.X);
            }
        }
    }

    /**
     * moves the mark down to the left.
     * 
     * @param move - the move has the information with what you want to move in what
     *             direction.
     * @requires the move is valid.
     * @requires field are valid.
     * @ensures the field left beneath has currentplayer.getmark.
     */
    public void moveDownLeft(Move move) {
        int[] fields = move.getMarblePositions();
        for (int i = 0; i < fields.length; i++) {
            if (getField(fields[i] - 11).getMark() != game.getCurrentPlayer().getMark() 
                    && getField(fields[i] - 11).getMark() != Mark.X) {
                if (game.getNumberOfPlayers() == 4) {
                    if (getField(fields[i] - 11).getMark() 
                            != game.getCurrentPlayer().getPartnerMark()) {                   
                        int opponentPosition = fields[i] - 11;
                        sumito(opponentPosition, -11);
                    }
                } else {
                    int opponentPosition = fields[i] - 11;
                    sumito(opponentPosition, -11);
                }
            }
            getField(fields[i] - 11).setMark(getField(fields[i]).getMark());
            if (!fieldInArray(fields, fields[i] + 11)) {
                getField(fields[i]).setMark(Mark.X);
            }
        }
    }

    /**
     * Executing the given move.
     * @param move - move of the current player
     */
    public void executeMove(Move move) {
        int direction = move.getDirection();
        int marblesOfOpponents = 0;
        for (int i = 0; i < index.length; i++) {
            if (getField(index[i]).getMark() != game.getCurrentPlayer().getMark() 
                    && getField(index[i]).getMark() != Mark.X) {
                if (game.getNumberOfPlayers() == 4) {
                    if (getField(index[i]).getMark() != game.getCurrentPlayer().getPartnerMark()) {
                        marblesOfOpponents++;
                    }
                } else {
                    marblesOfOpponents++;
                }
            }
        }
        switch (direction) {
            case (0): { // UpRight
                moveUpRight(move);
                break;
            }
            case (1): { // Right
                moveRight(move);
                break;
            }
            case (2): { // DownRight
                moveDownRight(move);
                break;
            }
            case (3): { // DownLeft
                moveDownLeft(move);
                break;
            }
            case (4): { // Left
                moveLeft(move);
                break;
            }
            case (5): { // UpLeft
                moveUpLeft(move);
                break;
            }
            default: {
                // throws illegalDirectionexception
    
            }
        }
        int newMarblesOfOpponents = 0;
        for (int i = 0; i < index.length; i++) {
            if (getField(index[i]).getMark() != game.getCurrentPlayer().getMark() 
                    && getField(index[i]).getMark() != Mark.X) {
                if (game.getNumberOfPlayers() == 4) {
                    if (getField(index[i]).getMark() != game.getCurrentPlayer().getPartnerMark()) {
                        newMarblesOfOpponents++;
                    }
                } else {
                    newMarblesOfOpponents++;
                }
            }
        }
        if (newMarblesOfOpponents < marblesOfOpponents) {
            game.getCurrentPlayer().upPoints();
        }
    }

    /**
     * returns the current state of the board.
     */
    public ArrayList<String> currentBoard() {
        currentBoard = new ArrayList<>();
        currentBoard.add("--------- " + getField(95).getMark() + " | " + getField(96).getMark() + " | "
                + getField(97).getMark() + " | " + getField(98).getMark() + " | " + getField(99).getMark()
                + " ---------");
        currentBoard.add("------- " + getField(84).getMark() + " | " + getField(85).getMark() + " | "
                + getField(86).getMark() + " | " + getField(87).getMark() + " | " + getField(88).getMark() + " | "
                + getField(89).getMark() + " -------");
        currentBoard.add("----- " + getField(73).getMark() + " | " + getField(74).getMark() + " | "
                + getField(75).getMark() + " | " + getField(76).getMark() + " | " + getField(77).getMark() + " | "
                + getField(78).getMark() + " | " + getField(79).getMark() + " -----");
        currentBoard.add("--- " + getField(62).getMark() + " | " + getField(63).getMark() + " | "
                + getField(64).getMark() + " | " + getField(65).getMark() + " | " + getField(66).getMark() + " | "
                + getField(67).getMark() + " | " + getField(68).getMark() + " | " + getField(69).getMark() + " ---");
        currentBoard.add("- " + getField(51).getMark() + " | " + getField(52).getMark() + " | " + getField(53).getMark()
                + " | " + getField(54).getMark() + " | " + getField(55).getMark() + " | " + getField(56).getMark()
                + " | " + getField(57).getMark() + " | " + getField(58).getMark() + " | " + getField(59).getMark() 
                + " -");
        currentBoard.add("--- " + getField(41).getMark() + " | " + getField(42).getMark() + " | "
                + getField(43).getMark() + " | " + getField(44).getMark() + " | " + getField(45).getMark() + " | "
                + getField(46).getMark() + " | " + getField(47).getMark() + " | " + getField(48).getMark() + " ---");
        currentBoard.add("----- " + getField(31).getMark() + " | " + getField(32).getMark() + " | "
                + getField(33).getMark() + " | " + getField(34).getMark() + " | " + getField(35).getMark() + " | "
                + getField(36).getMark() + " | " + getField(37).getMark() + " -----");
        currentBoard.add("------- " + getField(21).getMark() + " | " + getField(22).getMark() + " | "
                + getField(23).getMark() + " | " + getField(24).getMark() + " | " + getField(25).getMark() + " | "
                + getField(26).getMark() + " -------");
        currentBoard.add("--------- " + getField(11).getMark() + " | " + getField(12).getMark() + " | "
                + getField(13).getMark() + " | " + getField(14).getMark() + " | " + getField(15).getMark()
                + " ---------");
        return currentBoard;
    }

    /**
     * inquires if the move is valid. Rules: you can move max 3 marbles of your
     * own/your team. you can move less opposite marbles than you move your
     * own/team's. you can only move the opposites marbles into a free space or off
     * the board. you can only move one (1) space. you can only move a column if it
     * at least contains one of your own marbles. you cannot move your own or team's
     * marble off the board. if the move is a side-step move, all of the spaces the
     * marbles are moving to have to be free. can only move in-line if the first
     * marble is your own. side-step can be done by both team members.
     * 
     * @return true if the move meets the game rules.
     * @requires move != null.
     * @ensures result = true if move is valid && result = false if move is invalid
     */
    public boolean validMove(Move move) { 
        int[] moveMarblePositions = move.getMarblePositions();
        int numberOfPlayers = game.getNumberOfPlayers();
        int moveDirection = move.getDirection();
        int marbleLength = moveMarblePositions.length;

        if (numberOfPlayers != 4) {
            if (!(isMarbleOfPlayer(moveMarblePositions) && isOnSameLine(moveMarblePositions)
                    && newPositionIsField(moveMarblePositions, moveDirection) && isFields(moveMarblePositions))) {
                return false;
            }
        } else {
            if (!isMarbleOfTeam(moveMarblePositions) || !isOnSameLine(moveMarblePositions)
                    || !newPositionIsField(moveMarblePositions, moveDirection) || !isFields(moveMarblePositions)) { 
                return false;
            }
        }
        switch (moveDirection) {
            case (0): {
                if (marbleLength == 1 || !isOnDiagonal(moveMarblePositions, 11)) {
                    for (int i = 0; i < marbleLength; i++) {
                        if (getField(moveMarblePositions[i]).getMark() == Mark.X) {
                            return false;
                        }
                        if (getField(moveMarblePositions[i] + 11).getMark() != Mark.X) {
                            return false;
                        }
                    }
                    return true;
                } else {
                    int leadingMarble = getMax(moveMarblePositions); // als voorste leeg dan zetten anders
                    if (getField(leadingMarble + 11).getMark() == Mark.X) {
                        return true;
                    } else {
                        return isPushable(leadingMarble, marbleLength, 11);
                    }
                }
            }
            case (1): {
                if (marbleLength == 1 || !isOnDiagonal(moveMarblePositions, 1)) {
                    for (int i = 0; i < marbleLength; i++) {
                        if (getField(moveMarblePositions[i]).getMark() == Mark.X) {
                            return false;
                        }
                        if (getField(moveMarblePositions[i] + 1).getMark() != Mark.X) {
                            return false;
                        }
                    }
                    return true;
                } else {
                    int leadingMarble = getMax(moveMarblePositions); // als voorste leeg dan zetten anders
                    if (getField(leadingMarble + 1).getMark() == Mark.X) {
                        return true;
                    } else {
                        return isPushable(leadingMarble, marbleLength, 1);
                    }
                }
            }
            case (2): {
                if (marbleLength == 1 || !isOnDiagonal(moveMarblePositions, 10)) {
                    for (int i = 0; i < marbleLength; i++) {
                        if (getField(moveMarblePositions[i]).getMark() == Mark.X) {
                            return false;
                        }
                        if (getField(moveMarblePositions[i] - 10).getMark() != Mark.X) {
                            return false;
                        }
                    }
                    return true;
                } else {
                    int leadingMarble = getMin(moveMarblePositions); // als voorste leeg dan zetten anders
                    if (getField(leadingMarble - 10).getMark() == Mark.X) {
                        return true;
                    } else {
                        return isPushable(leadingMarble, marbleLength, -10);
                    }
                }
            }
            case (3): {
                if (marbleLength == 1 || !isOnDiagonal(moveMarblePositions, 11)) {
                    for (int i = 0; i < marbleLength; i++) {
                        if (getField(moveMarblePositions[i]).getMark() == Mark.X) {
                            return false;
                        }
                        if (getField(moveMarblePositions[i] - 11).getMark() != Mark.X) {
                            return false;
                        }
                    }
                    return true;
                } else {
                    int leadingMarble = getMin(moveMarblePositions); // als voorste leeg dan zetten anders
                    if (getField(leadingMarble - 11).getMark() == Mark.X) {
                        return true;
                    } else {
                        return isPushable(leadingMarble, marbleLength, -11);
                    }
                }
            }
            case (4): {
                if (marbleLength == 1 || !isOnDiagonal(moveMarblePositions, 1)) {
                    for (int i = 0; i < marbleLength; i++) {
                        if (getField(moveMarblePositions[i]).getMark() == Mark.X) {
                            return false;
                        }
                        if (getField(moveMarblePositions[i] - 1).getMark() != Mark.X) {
                            return false;
                        }
                    }
                    return true;
                } else {
                    int leadingMarble = getMin(moveMarblePositions); // als voorste leeg dan zetten anders
                    if (getField(leadingMarble - 1).getMark() == Mark.X) {
                        return true;
                    } else {
                        return isPushable(leadingMarble, marbleLength, -1);
                    }
                }
            }
            case (5): {
                if (marbleLength == 1 || !isOnDiagonal(moveMarblePositions, 10)) {
                    for (int i = 0; i < marbleLength; i++) {
                        if (getField(moveMarblePositions[i]).getMark() == Mark.X) {
                            return false;
                        }
                        if (getField(moveMarblePositions[i] + 10).getMark() != Mark.X) {
                            return false;
                        }
                    }
                    return true;
                } else {
                    int leadingMarble = getMax(moveMarblePositions); // als voorste leeg dan zetten anders
                    if (getField(leadingMarble + 10).getMark() == Mark.X) {
                        return true;
                    } else {
                        return isPushable(leadingMarble, marbleLength, 10);
                    }
                }
            }
            default: {
                // throw new IllegalDirectionException() e;
            }
        }
        return false;
    }

    /**
     * sub method of valid move, can the marbles be pushed.
     * @param leadingMarble - the marble of the currentplayer in the front of the movement
     * @param marbleLength - the number of marbles to be moved.
     * @param movement - the directionvalue
     * @requires leadingMarble is in INDEX
     * @requires marbleLength <= 3 && marbleLenght >= 1
     * @requires movement = (1 || -1 || 10 || -10 || 11 || -11)
     * @ensures if 4 player game, the leadingMarble is of currentplayer
     * @ensures if 4 player game, at least 1 of the marbles is of currentplayer
     */
    private boolean isPushable(int leadingMarble, int marbleLength, int movement) {
        int currentIndex = leadingMarble;
        int nextMarbles = 0;
        if (game.getNumberOfPlayers() != 4) {
            boolean notEmpty = true;
            while (notEmpty) {
                currentIndex += movement;
                if (!isField(currentIndex)) {
                    notEmpty = false;
                } else {
                    if (getField(currentIndex).getMark() == game.getCurrentPlayer().getMark()) {
                        return false;
                    } else if (getField(currentIndex).getMark() != Mark.X) {
                        nextMarbles++;
                    } else {
                        notEmpty = false;
                    }
                }
            }
            return marbleLength > nextMarbles;

        } else {
            boolean notEmpty = true;
            if (getField(leadingMarble).getMark() != game.getCurrentPlayer().getMark()) {
                return false;
            }  
            while (notEmpty) {
                currentIndex += movement;
                if (!isField(currentIndex)) {
                    notEmpty = false;
                } else {
                    if (getField(currentIndex).getMark() == game.getCurrentPlayer().getMark()
                            || getField(currentIndex).getMark() == game.getCurrentPlayer().getPartnerMark()) {
                        return false;
                    } else if (getField(currentIndex).getMark() != Mark.X) {
                        nextMarbles++;
                    } else {
                        notEmpty = false;
                    }
                }
            }
            return marbleLength > nextMarbles;
        }
    }
    
    /**
     * submethod to get the max value of the marbleIndexes.
     * used for getting the leading marble
     * 
     * @param moveMarblePositions - the to be moved marbles
     */
    private int getMax(int[] moveMarblePositions) {
        int max = 0;
        for (int i = 0; i < moveMarblePositions.length; i++) {
            if (moveMarblePositions[i] > max) {
                max = moveMarblePositions[i];
            }
        }
        return max;
    }

    /**
     * submethod to get the min value of the marbleIndexes.
     * used for getting the leading marble
     * 
     * @param moveMarblePositions - the to be moved marbles
     */
    private int getMin(int[] moveMarblePositions) {
        int max = 100;
        for (int i = 0; i < moveMarblePositions.length; i++) {
            if (moveMarblePositions[i] < max) {
                max = moveMarblePositions[i];
            }
        }
        return max;
    }

    /**
     * this gives the direction of the to be moved marbles.
     * 
     * @param moveMarblePositions - to be moved marbles
     * @param direction           - Right diagonal(/) = 11, left diagonal(\) = 10,
     *                            horizontal(-) = 1
     */
    private boolean isOnDiagonal(int[] moveMarblePositions, int direction) {
        int length = moveMarblePositions.length;
        switch (length) {
            case (1): {
                return true;
            }
            case (2): {
                int differ = Math.abs(moveMarblePositions[0] - moveMarblePositions[1]);
                return differ == direction;
            }
            case (3): { // marblesposition a b c. |a - b| == |b - c|
                if ((moveMarblePositions[0] > moveMarblePositions[1] && moveMarblePositions[0] < moveMarblePositions[2])
                        || (moveMarblePositions[0] < moveMarblePositions[1]
                                && moveMarblePositions[0] > moveMarblePositions[2])) {
                    int differ1 = Math.abs(moveMarblePositions[0] - moveMarblePositions[1]);
                    int differ2 = Math.abs(moveMarblePositions[0] - moveMarblePositions[2]);
                    return (differ1 == differ2 && differ1 == direction);
                }
                if ((moveMarblePositions[1] > moveMarblePositions[0] && moveMarblePositions[1] < moveMarblePositions[2])
                        || (moveMarblePositions[1] < moveMarblePositions[0]
                                && moveMarblePositions[1] > moveMarblePositions[2])) {
                    int differ1 = Math.abs(moveMarblePositions[1] - moveMarblePositions[0]);
                    int differ2 = Math.abs(moveMarblePositions[1] - moveMarblePositions[2]);
                    return (differ1 == differ2 && differ1 == direction);
                }
                if ((moveMarblePositions[2] > moveMarblePositions[0] && moveMarblePositions[2] < moveMarblePositions[1])
                        || (moveMarblePositions[2] < moveMarblePositions[0]
                                && moveMarblePositions[2] > moveMarblePositions[1])) {
                    int differ1 = Math.abs(moveMarblePositions[2] - moveMarblePositions[0]);
                    int differ2 = Math.abs(moveMarblePositions[2] - moveMarblePositions[1]);
                    return (differ1 == differ2 && differ1 == direction);
                }
                break;
            }
            default: {
            }
        }
        return false;
    }

    /**
     * submethod for isValidMove
     * checks whether the new position for the marbles is still in field.
     * 
     * @param moveMarblePositions - the to be moved marbles
     * @param moveDirection - the direction of the movement
     * @requires moveDirection is between 0 and 5
     * @requires moveMarblePositions.length between 1 and 3
     */
    private boolean newPositionIsField(int[] moveMarblePositions, int moveDirection) {
        for (int i = 0; i < moveMarblePositions.length; i++) {
            switch (moveDirection) {
                case (0): { // UpRight +11
                    if (!isField(moveMarblePositions[i] + 11)) {
                        return false;
                    }
                    break;
                }
                case (1): { // Right +1
                    if (!isField(moveMarblePositions[i] + 1)) {
                        return false;
                    }
                    break;
                }
                case (2): { // DownRight -10
                    if (!isField(moveMarblePositions[i] - 10)) {
                        return false;
                    }
                    break;
                }
                case (3): { // DownLeft -11
                    if (!isField(moveMarblePositions[i] - 11)) {
                        return false;
                    }
                    break;
                }
                case (4): { // Left -1
                    if (!isField(moveMarblePositions[i] - 1)) {
                        return false;
                    }
                    break;
                }
                case (5): { // UpLeft +10
                    if (!isField(moveMarblePositions[i] + 10)) {
                        return false;
                    }
                    break;
                }
                default: {
    
                }
            }
        }
        return true;

    }

    /**
     * checks if the marbles are either currentplayer.getmark or currentplayer.getpartnermark
     * @param moveMarblePositions - to be moved marbles
     */
    private boolean isMarbleOfTeam(int[] moveMarblePositions) {
        for (int i = 0; i < moveMarblePositions.length; i++) {
            if (getField(moveMarblePositions[i]).getMark() != game.getCurrentPlayer().getMark()
                    && getField(moveMarblePositions[i]).getMark() != game.getCurrentPlayer().getPartnerMark()) {
                return false;
            }
        }
        return true;

    }

    /**
     * Checks if the marbles on the given positions are on board.
     * 
     * @param moveMarblePositions - the to be moved marbles
     */
    private boolean isFields(int[] moveMarblePositions) {
        for (int i = 0; i < moveMarblePositions.length; i++) {
            if (!isField(moveMarblePositions[i])) {
                return false;
            }
        }
        return true;

    }

    /**
     * Checks if the marbles on the given positions are from the player.
     * 
     * @param moveMarblePositions - the to be moved marbles
     * @ensures player.getMark == fields.getmark
     */
    private boolean isMarbleOfPlayer(int[] moveMarblePositions) {
        for (int i = 0; i < moveMarblePositions.length; i++) {
            if (game.getCurrentPlayer().getMark() != getField(moveMarblePositions[i]).getMark()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether the marbles are next to each other.
     * 
     * @param moveMarblePositions - the marbles that need to be moved
     */
    private boolean isOnSameLine(int[] moveMarblePositions) {
        int length = moveMarblePositions.length;
        switch (length) {
            case (1): {
                return true;
            }
            case (2): {
                int differ = Math.abs(moveMarblePositions[0] - moveMarblePositions[1]);
                if (differ == 1 || differ == 10 || differ == 11) {
                    return true;
                }
                break;
            }
            case (3): { // marblesposition a b c. |a - b| == |b - c|
                if ((moveMarblePositions[0] > moveMarblePositions[1] && moveMarblePositions[0] < moveMarblePositions[2])
                        || (moveMarblePositions[0] < moveMarblePositions[1]
                                && moveMarblePositions[0] > moveMarblePositions[2])) {
                    int differ1 = Math.abs(moveMarblePositions[0] - moveMarblePositions[1]);
                    int differ2 = Math.abs(moveMarblePositions[0] - moveMarblePositions[2]);
                    return (differ1 == differ2 && (differ1 == 1 || differ1 == 10 || differ1 == 11));
                }
                if ((moveMarblePositions[1] > moveMarblePositions[0] && moveMarblePositions[1] < moveMarblePositions[2])
                        || (moveMarblePositions[1] < moveMarblePositions[0]
                                && moveMarblePositions[1] > moveMarblePositions[2])) {
                    int differ1 = Math.abs(moveMarblePositions[1] - moveMarblePositions[0]);
                    int differ2 = Math.abs(moveMarblePositions[1] - moveMarblePositions[2]);
                    return (differ1 == differ2 && (differ1 == 1 || differ1 == 10 || differ1 == 11));
                }
                if ((moveMarblePositions[2] > moveMarblePositions[0] && moveMarblePositions[2] < moveMarblePositions[1])
                        || (moveMarblePositions[2] < moveMarblePositions[0]
                                && moveMarblePositions[2] > moveMarblePositions[1])) {
                    int differ1 = Math.abs(moveMarblePositions[2] - moveMarblePositions[0]);
                    int differ2 = Math.abs(moveMarblePositions[2] - moveMarblePositions[1]);
                    return (differ1 == differ2 && (differ1 == 1 || differ1 == 10 || differ1 == 11));
                }
                break;
            }
            default: {
            }
        }
        return false;
    }
    
    /**
     * returns the String representation of the current board.
     */
    public String toString() {
        this.currentBoard = currentBoard();
        String s = "";
        for (int i = 0; i < 9; i++) {
            s += currentBoard.get(i) + "               " + NUMBERING.get(i) + "\n";
        }
        return s;
    }
}
