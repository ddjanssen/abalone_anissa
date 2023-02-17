package abaloneview;

import java.util.Observable;

import abalonecontrol.Game;
import abalonecontrol.LocalGame;
import abalonemodel.Board;
import abalonemodel.Player;

/**
 * 
 * @author Anissa
 *
 */
public class Tui extends View {

    public Tui(String[] playerNames, Class<LocalGame> game) {
        super(playerNames, game);
    }
    
    public Tui() { }

    @Override
    public boolean askToContinuePlaying() {
        System.out.println("Nice game huh? Want to play another one?");
        System.out.println("y/n");
        String wantToPlay = "";
        while (wantToPlay.equals("")) {
            if (input.hasNextLine()) {
                wantToPlay = input.nextLine();
                switch (wantToPlay) {
                    case ("n"): {
                        return false;
                    }
                    case ("y"): {
                        return true;
                    }
                    default: {
                        System.out.println(wantToPlay);
                        wantToPlay = "";
                        System.out.println("Please enter y or n");
                        break;
                        
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void printBoard(Board board) {
        System.out.println(board.toString());
        
    }

    @Override
    public void showWinners(Board board, Player[] players) {
        int numberOfWinners = board.getWinners().size();
        if (board.getWinners().size() == 1) {
            System.out.println("Concratulations " + board.getWinners().get(0).getName() 
                    + ", you have won the game!");
            
        }
        if (board.getWinners().size() == 2) {
            if (players.length == 4) {
                System.out.println(board.getWinners().get(0).getName() + "," + board.getWinners().get(1).getName() 
                        +  ", you have won");
            } else {
                System.out.println(board.getWinners().get(0).getName() + "," + board.getWinners().get(1).getName() 
                        +  ", you have tied");
            }
        }
        if (board.getWinners().size() == 3) {
            System.out.println(board.getWinners().get(0).getName() + "," + board.getWinners().get(1).getName() 
                    + board.getWinners().get(2).getName() + ", you have tied");
        }
        if (board.getWinners().size() == 4) {
            System.out.println(board.getWinners().get(0).getName() + "," + board.getWinners().get(1).getName() 
                    +  board.getWinners().get(2).getName() + "," + board.getWinners().get(3).getName() 
                    + ", you have tied");
        }

        
    }

}
