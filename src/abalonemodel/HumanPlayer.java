package abalonemodel;

import abalonecontrol.Game;

import java.util.Scanner;

/**
 * A Class of a HumanPlayer which extends a Player.
 * 
 * @author Anissa
 *
 */
public class HumanPlayer extends Player {

    private Scanner input = new Scanner(System.in);

    /**
     * Creates a new Marble object.
     * 
     * @requires name != null
     * @requires mark != null
     * @param name - name of the player
     * @param game - the colour of the player
     */
    public HumanPlayer(String name, Game game) {
        super(name, game);
    }

    /**
     * DetermineMove gives the option for the human player to choose a move. This
     * move could be done by 1,2 or 3 marbles at the same time. First the player
     * gives the selection of marbles for the move. this input is checked. the rest
     * is tested in other method. when (partly) valid, player gives the direction
     * where to go.
     * 
     * @requires board != null
     * @ensures input.lenght < 4
     * @ensures input.getType = int
     * @ensures input.isfield
     */
    @Override
    public Move/* Move */ determineMove(Board board) {
        boolean choosingMarbles = true;
        boolean chooseMove = false;
        int moveDirection = -1;
        int[] intMarbles = null;
        String a = "";

        String prompt = "> " + getName() + " (" + mark.toString() + ") which marbles do you want to move?";
        System.out.println(prompt);
        while (choosingMarbles) {
            System.out.println("Give your answer like this: 1,2,3 / 1,2 / 1 ");
            if (input.hasNext()) {
                a = input.nextLine();
                a = a.strip();
                String[] split = a.split(",");
                // is the input not bigger than 4 integers
                if (split.length < 4) {
                    intMarbles = new int[split.length];
                    boolean allNumbers = true;
                    for (int i = 0; i < split.length; i++) {
                        try { // is the string convertible to int.
                            int input = Integer.parseInt(split[i]);
                            if (board.isField(input)) {
                                intMarbles[i] = input;
                            } else {
                                System.out.println("Input is not a field");
                                allNumbers = false;
                                break;
                            }

                        } catch (NumberFormatException nfe) {
                            System.out.println("Please give your answer in integers " + "seperated with comma");
                            allNumbers = false;
                            break;
                        }
                    }
                    if (allNumbers) {
                        choosingMarbles = false;
                        chooseMove = true;

                    }

                } else {
                    System.out.println("Please give 1 upto 3 integers separated with a comma.");
                }
            }
        }
        System.out.println("where do you want to move these marbles?");
        String optionalMoves = "For up right choose 0 \n" + "For right choose 1 \n" + "For down right choose 2 \n"
                + "For down left choose 3 \n" + "For left choose 4 \n" + "For up left choose 5 \n";
        System.out.println(optionalMoves);
        while (chooseMove) {
            if (input.hasNextInt()) {
                if (!((moveDirection = input.nextInt()) < 6 && moveDirection >= 0)) {
                    System.out.println("Please choose between 0 and 5");
                } else {
                    chooseMove = false;
                }
            }
        }
        return new Move(intMarbles, moveDirection);
    }

}
