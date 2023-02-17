package abalonenetwork;

import abalonecontrol.ClientGame;
import abalonemodel.Mark;
import abalonemodel.Move;
import abaloneprotocol.Protocol;
import abalonetest.abaloneexceptions.IllegalMoveException;
import abaloneview.ClientTui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;



public class Client implements Runnable {
    
    private Socket socket;
    private String name;
    private ClientTui view;
    private int numberOfPlayers;
    private BufferedReader input;
    private BufferedWriter output;
    private Mark myMark;
    private Mark currentplayer;
    private String serverName;
    private ClientGame game;
    

    /**
     * Creates a new instance of <code>Client</code>.
     * @param name - the name of the clientplayer
     * @param socket - an endpoint for communicationbetween two machines
     * @param view - the user interface
     * @param numberOfPlayers - the number of players for the game
     */
    public Client(String name, Socket socket, ClientTui view, int numberOfPlayers) {
        this.name = name;
        this.socket = socket;
        this.view = view;
        this.numberOfPlayers = numberOfPlayers;
        try {
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendConnectMessage();
    }
    
    /**
     * Looks at the beginning of the message and calls the proper method.
     * @param msg - message from the server
     * @throws IllegalMoveException - when the server sends a invalid move
     */
    public void receiveMessage(String msg) throws IllegalMoveException {
        System.out.println("Receive: " + msg);
        if (msg.startsWith(Protocol.DRAWMESSAGE)) {
            receiveDraw(msg);
        }
        if (msg.startsWith(Protocol.SERVERRESPONSE)) { 
            receiveResponse(msg);
        }
        if (msg.startsWith(Protocol.SERVERSTARTGAME)) {
            receiveStartGame(msg);
        }
        if (msg.startsWith(Protocol.DISTRIBUTECOLOR)) {
            receiveDistributeColour(msg);
        }
        if (msg.startsWith(Protocol.CONFIRMMOVE)) {
            receiveConfirmMove(msg);
        } else if (msg.startsWith(Protocol.MAKEMOVE)) {
            receiveMakeMove(msg);
        }
        if (msg.startsWith(Protocol.WINNER)) {
            receiveWinner(msg);
        }
        if (msg.startsWith(Protocol.DISCONNECT)) {
            receiveDisconnect(msg);
        }
    }

    private void receiveDraw(String msg) {
        String[] msgArgs = getArguments(msg);
        ClientTui.printDrawMessage(msgArgs);
        
    }

    private void receiveResponse(String msg) {
        String[] msgArgs = getArguments(msg);
        String serverName = Arrays.deepToString(msgArgs);
        this.serverName = serverName;
        
    }

    private void receiveStartGame(String msg) {
        String[] names = new String[numberOfPlayers];
        switch (myMark) {
            case B: {
                names[0] = name;
                break;
            }
            case W: {
                names[1] = name;
                break;
            }
            case C: {
                names[2] = name;
                break;
            }
            case R: {
                names[3] = name;
                break;
            }
            default: { }
        }
        for (int i = 0; i < names.length; i++) {
            if (names[i] == null) {
                names[i] = "Other" + i;
            }
        }    
        this.game = new ClientGame(names, view);
        currentplayer = this.game.getCurrentPlayer().getMark();
        sendConfirmStart();
        if (currentplayer == myMark) {
            view.printBoard(game.getBoard());
            Move move = this.game.getCurrentPlayer().makeMove(this.game.getBoard());
            sendMakeMove(move);
        }
    }

    private void receiveDistributeColour(String msg) {
        String[] msgArgs = getArguments(msg);
        String colour = msgArgs[0];
        switch (colour) {
            case ("B"): {
                myMark = Mark.B;
                break;
            }
            case ("R"): {
                myMark = Mark.R;
                break;
            }
            case ("W"): {
                myMark = Mark.W;
                break;
            }
            case ("C"): {
                myMark = Mark.C;
                break;
            }
            default: { }
        }
    }

    private void receiveMakeMove(String msg) {
        int isValid = -1;
        String[] msgArgs = getArguments(msg);
        Move move = serverMoveToMyMove(msgArgs);
        if (game.getBoard().validMove(move)) {
            isValid = 1;
        } else {
            isValid = 0;
        }
        sendAcceptMove(isValid);
    }

    private void receiveConfirmMove(String msg) throws IllegalMoveException {
        String[] msgArgs = getArguments(msg);
        Move move = serverMoveToMyMove(msgArgs);
        game.doMove(move);
        switch (numberOfPlayers) {
            case (2): {
                currentplayer = currentplayer.getNextPlayerTwo();
                game.setCurrentPlayer(game.getNextPlayer());
                break;
            }
            case (3): {
                currentplayer = currentplayer.getNextPlayerThree();
                game.setCurrentPlayer(game.getNextPlayer());
                break;
            }
            case (4): {
                currentplayer = currentplayer.getNextPlayerFour();
                game.setCurrentPlayer(game.getNextPlayer());
                break;
            }
            default: { //TODO throw new IllegalAmountOfPlayerException();
                break; }
        }
        if (currentplayer == myMark) {
            view.printBoard(game.getBoard());
            Move myMove = this.game.getPlayer(myMark).makeMove(this.game.getBoard());
            sendMakeMove(myMove);
        }
    }

    private void receiveWinner(String msg) {
        String[] msgArgs = getArguments(msg);
        if (msgArgs[0].contains(name)) {
            System.out.println("You have won"); 
        } else {
            System.out.println("you lost");
        }
        
    }
    
    private void receiveDisconnect(String msg) {
        String[] msgArgs = getArguments(msg);
        view.playerDisconnected(msgArgs);
        
    }
    
    private String[] getArguments(String msg) {
        String[] message = msg.split(Protocol.SPLIT);
        String[] msgArgs = new String[message.length - 1];
        for (int i = 1; i < message.length; i++) {
            msgArgs[i - 1] = message[i];
        }
        return msgArgs;
    }
    
    /**
     * submethod of receiveStartGame().
     */
    private void sendConfirmStart() {
        send(Protocol.CLIENTCONFIRMSTART + Protocol.SPLIT + name);
        
    }
    
    /**
     * first message which is send to the server.
     */
    public void sendConnectMessage() {
        send(Protocol.CONNECTMESSAGE + Protocol.SPLIT + name 
                + Protocol.SPLIT + numberOfPlayers + Protocol.SPLIT + "0000");
    }
    
    /**
     * submethod for sending own move.
     * @param move - move on own board.
     */
    private void sendMakeMove(Move move) {
        int direction = move.getDirection();
        int[] marbles = move.getMarblePositions();
        String serverMarbles = game.convertIndexToNaive(marbles);
        send(Protocol.MAKEMOVE + Protocol.SPLIT + serverMarbles + Protocol.SPLIT + direction);
        
        
    }
    
    /**
     * submethod for receiveMakeMove().
     * @param isValid - if the received move is valid
     */
    private void sendAcceptMove(int isValid) {
        send(Protocol.ACCEPTMOVE + Protocol.SPLIT + isValid);
        
    }
    
    /**
     * submethod to send messages to the server.
     * @param msg - the message according to the protocol for communication with the server
     */
    private void send(String msg) {
        try {
            System.out.println("Send: " + msg);
            output.write(msg);
            output.newLine();
            output.flush();
        } catch (IOException e) {
            closeConnection();
        }
        
    }
    
    /**
     * converts the move from the server to the move of <code>ClientGame</code>.
     * @param msgArgs - the to be moved marbles and direction in a string
     */
    private Move serverMoveToMyMove(String[] msgArgs) {
        int direction = -1;
        String marbles = msgArgs[0];
        String dir = msgArgs[1];
        //TODO
        
        try { 
            direction = Integer.parseInt(dir);
        } catch (NumberFormatException nfe) {
            System.out.println("message doesn't have a valid direction");
        }
        
        int[] marbleIndex = game.convertNaiveToIndex(marbles);  // gebeurd dit in Cgame of board
        Move move = new Move(marbleIndex, direction);
        return move;
    }

    private void closeConnection() {
        view.serverDisconnected();
        
    }

    @Override
    public void run() {
        String msg;
        while (true) {
            msg = "";
            while (msg.equals("")) {
                try {
                    msg = input.readLine();
                    if (msg == null) {
                        closeConnection();
                    }
                } catch (IOException e) {
                    view.serverDisconnected();
                    return;
                }
            }
            try {
                receiveMessage(msg);
            } catch (IllegalMoveException e) {
                System.out.println("ERROR: " + e.getMessage());;
            }
        }
        
    }
    

}
