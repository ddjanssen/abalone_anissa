package abalonecontrol;

import abalonemodel.Move;
import abalonemodel.Player;
import abalonenetwork.ClientHandler;
import abaloneview.ServerTui;

import java.util.ArrayList;


/**
 * Class <code>ServerGame</code> extends <code>LocalGame</code>.
 * @author Anissa
 *
 */
public class ServerGame extends LocalGame {
    ArrayList<ClientHandler> handlers;
    private int answerFromAll = 0;
    private Move move;

    /**
     * Creates a new instance of <code>ServerGame</code>.
     * @param playernames - the names of the players
     * @param serverView - the interface for the server
     * @param handlers - client handlers for each player
     */
    public ServerGame(String[] playernames, ServerTui serverTui, ArrayList<ClientHandler> handlers) {
        super(playernames, serverTui);
        this.handlers = handlers;
        for (ClientHandler handler : handlers) {
            handler.setGame(this);
        } 
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public int getAnswerFromAll() {
        return answerFromAll;
    }

    public void setAnswerFromAll(int answerFromAll) {
        this.answerFromAll = answerFromAll;
    }

    /**
     * checks whether a move is legal.
     * 
     * @param m - the move of the current networked player
     * 
     * @ensures the move is from the currentplayer
     */
    public boolean isLegalMove(Move m) {
        return board.validMove(m);
    }

    /**
     * Executes move and sends protocol.MC to all clienthandlers
     * 
     */
    public synchronized void doMove() {
        for (ClientHandler handler : handlers) {
            handler.sendConfirmMove(move);
        }
        board.executeMove(move);
        move = null;
        notify();
    }

    /**
     * Closes the connection of the given clientHandler.
     * Sends to all clientHandlers that a certain client's connection is lost.
     * @param clientHandler - the client handler of the lost connection
     */
    public void lostConnection(ClientHandler clientHandler) {
        for (ClientHandler handler: handlers) {
            if (clientHandler != handler) {
                handler.sendLostConnection(clientHandler.getHandlerName());
                handler.sendGameOver(board.getWinners());
            }
            handler.sendLostConnection(clientHandler.getHandlerName());
        }
    }

    /**
     * sends to all clients that there is a client disconnected.
     * @param clientHandler - handler of disconnected client
     */
    public void closeHandler(ClientHandler clientHandler) {
        for (ClientHandler h : handlers) {
            h.sendLostConnection(clientHandler.getHandlerName());
        }
    }

    /**
     * plays the servergame.
     */
    @Override
    public void play() {
        for (int i = 0; i < handlers.size(); i++) {
            handlers.get(i).sendColours(i);
            handlers.get(i).sendServerStartGame();
        }
        while (answerFromAll < players.length) {
            try {
                synchronized (this) {
                    wait(); 
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setAnswerFromAll(0);
        int turns = 0;
        while (!board.gameEnd() && turns < 96) {
            try {
                synchronized (this) {
                    wait(); 
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setAnswerFromAll(0);
            currentPlayer = getNextPlayer();
            turns++;
        }
        sendWinners(board.getWinners());
        view.showWinners(board, players); // if turns = 96 draw
        
    }

    /**
     * sends the winners to all clients.
     * @param winners - arraylist of players who have won
     */
    public void sendWinners(ArrayList<Player> winners) {
        for (ClientHandler handler : handlers) {
            handler.sendGameOver(winners);
        }
        //stop
    }
    
    /**
     * sends the draw message with message to all players.
     * @param msg - information of the draw
     */
    public void sendDraw(String msg) {
        for (ClientHandler handler : handlers) {
            handler.sendDraw(msg);
        }
        //TODO stop server dign
    }

    /**
     * forward the MakeMove of the currentPlayer.
     * @param msg - makemove message of player
     * @param marbles - marble position on naive board
     * @param direction - direction of the move
     */
    public void sendMakeMove(String msg, String marbles, String direction) {
        for (ClientHandler handler : handlers) {
            handler.sendMakeMove(msg);
        }
        move = new Move(convertNaiveToIndex(marbles), Integer.parseInt(direction));
        
    }


}
