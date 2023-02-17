package abalonenetwork;

import abalonecontrol.ServerGame;
import abalonemodel.Move;
import abalonemodel.Player;
import abaloneprotocol.Protocol;
import abalonetest.abaloneexceptions.ProtocolException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {

    public final Server server;
    private final Socket socket;
    private BufferedReader input;
    private BufferedWriter output;
    private ServerGame game = null;
    private String name = "";

    /**
     * this is javadoc.
     * @param server - server
     * @param socket - socket
     */
    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String msg;
        while (true) {
            msg = "";
            while (msg.contentEquals("")) {
                try {
                    msg = input.readLine();
                    if (msg == null) {
                        lostConnection();
                        return;
                    }
                } catch (IOException e) {
                    lostConnection();
                }
            }
            try {
                receive(msg);
            } catch (ProtocolException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }


    private void receive(String msg) throws ProtocolException {
        System.out.println("Received: " + msg + ". From: " + name);
        if (msg.startsWith(Protocol.CONNECTMESSAGE)) {
            receiveConnect(msg);
        }
        if (msg.startsWith(Protocol.CLIENTCONFIRMSTART)) {
            reveiveConfirmStart(msg);
        }
        if (msg.startsWith(Protocol.MAKEMOVE)) {
            receiveMakeMove(msg);
        }
        if (msg.startsWith(Protocol.ACCEPTMOVE)) {
            receiveAcceptMove(msg);
        }
        if (msg.startsWith(Protocol.CHAT)) {
            receiveChatRequest(msg);
        }
    }

    private void receiveChatRequest(String msg) {
        // TODO Auto-generated method stub
        
    }

    private void receiveAcceptMove(String msg) {
        String[] msgArgs = getMessageArguments(msg);
        if (msgArgs[0].equals("0")) {
            game.sendDraw("Invalid move of: " + game.getCurrentPlayer().getName() 
                    + ", detected by " + this.getHandlerName());
        } else {
            synchronized (game) {
                game.setAnswerFromAll(game.getAnswerFromAll() + 1);
                if (game.getAnswerFromAll() == game.getNumberOfPlayers()) {
                    game.doMove();
                }
            }
        }
    }

    public void sendDraw(String msg) {
        send(Protocol.DRAWMESSAGE + Protocol.SPLIT + msg);
        
    }

    private void send(String msg) {
        System.out.println("Sending " + msg);
        try {
            output.write(msg);
            output.newLine();
            output.flush();
        } catch (IOException e) {
            closeHandler();
        }
    }

    private String[] getMessageArguments(String msg) {
        String[] message = msg.split(Protocol.SPLIT);
        String[] msgArgs = new String[message.length - 1];
        for (int i = 1; i < message.length; i++) {
            msgArgs[i - 1] = message[i];
        }
        return msgArgs;
    }

    private void receiveMakeMove(String msg) {
        String[] msgArgs = getMessageArguments(msg);
        game.sendMakeMove(msg, msgArgs[0], msgArgs[1]);
        
    }

    private void reveiveConfirmStart(String msg) {
        synchronized (game) {
            game.setAnswerFromAll(game.getAnswerFromAll() + 1);
            if (game.getAnswerFromAll() == game.getNumberOfPlayers()) {
                game.notify();
            }
        }
    }

    private void receiveConnect(String msg) {
        String[] msgArgs = getMessageArguments(msg);
        int numberOfPlayers;
        if (msgArgs.length == 3) {
            name = msgArgs[0];
            try {
                numberOfPlayers = Integer.parseInt(msgArgs[1]);
            } catch (NumberFormatException nfe) {
                name = "";
                return;
            }
            if (numberOfPlayers >= 2 || numberOfPlayers <= 4) {
                server.addToQueue(numberOfPlayers, this);
                sendServerResponse(server.getName());
            }
            
        }
        
    }

    private void sendServerResponse(String name) {
        send(Protocol.SERVERRESPONSE + Protocol.SPLIT + name);
        
    }

    private void lostConnection() {
        if (game != null) {
            game.lostConnection(this);
        }
        closeHandler();
    }

    private void closeHandler() {
        game.closeHandler(this);
        try {
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGame(ServerGame serverGame) {
        game = serverGame;
    }

    /**
     * sends the checked move to the clients.
     * @param move - move of current player
     */
    public void sendConfirmMove(Move move) {
        int direction = move.getDirection();
        int[] marbles = move.getMarblePositions();
        String serverMarbles = game.convertIndexToNaive(marbles);
        send(Protocol.CONFIRMMOVE + Protocol.SPLIT + serverMarbles + Protocol.SPLIT + direction);
    }

    public void sendServerStartGame() {
        send(Protocol.SERVERSTARTGAME);
    }

    public String getHandlerName() {
        return name;
    }

    public void sendLostConnection(String handlerName) {
        sendDraw("Player " + handlerName + " has disconnected");
        
    }

    /**
     * sends the winners of the game to the clients.
     * @param winners - arraylist of players who have won
     */
    public void sendGameOver(ArrayList<Player> winners) {
        int numberOfWinners = winners.size();
        switch (game.getNumberOfPlayers()) {
            case (2): {
                if (winners.size() == 1) {
                    send(Protocol.WINNER + Protocol.SPLIT + winners.get(0).getName());
                } else {
                    String msg = "Player " + winners.get(0).getName() + " and player " 
                            + winners.get(1).getName() +  " have same amount of points.";
                    sendDraw(Protocol.DRAWMESSAGE + Protocol.SPLIT + msg);
                }
                break;
            }
            case (3): {
                switch (numberOfWinners) {
                    case (1): {
                        send(Protocol.WINNER + Protocol.SPLIT + winners.get(0).getName());
                        break;
                    }
                    case (2): {
                        String msg = "Player " + winners.get(0).getName() + " and player " 
                                + winners.get(1).getName() +  " have same amount of points.";
                        sendDraw(Protocol.DRAWMESSAGE + Protocol.SPLIT + msg);
                        break;
                    }
                    case (3): {
                        String msg = "Player " + winners.get(0).getName() + ", player " + winners.get(1).getName() 
                                + " and player " + winners.get(2).getName() + " have same amount of points.";
                        sendDraw(Protocol.DRAWMESSAGE + Protocol.SPLIT + msg);
                        break;
                    }
                    default: { }
                }
                
                break;
            }
            case (4): {
                switch (numberOfWinners) {
                    case (2): {
                        send(Protocol.WINNER + Protocol.SPLIT + winners.get(0).getName() 
                                + " " + winners.get(1).getName());
                        break;
                    }
                    case (4): {
                        String msg = "Player " + winners.get(0).getName() + ", player " 
                                + winners.get(1).getName() + ", player " + winners.get(2).getName() 
                                + " and player " + winners.get(3) + " have same amount of points.";
                        sendDraw(Protocol.DRAWMESSAGE + Protocol.SPLIT + msg);
                        break;
                    }
                    default: { }
                break;
                }
                break;
            }
            default : { }
        }
        
    }

    /**
     * sends the colour to the player.
     * @param mark - the textual representation of a colour
     */
    public void sendColours(int mark) {
        String colour = "";
        switch (mark) {
            case (0): {
                colour = "B";
                break;
            }
            case (1): {
                colour = "W";
                break;
            }
            case (2): {
                colour = "C";
                break;
            }
            case (3): {
                colour = "R";
                break;
            }
            default: { // throw new ProtocolException("Trying to send invalid colour");
            }
        }
        send(Protocol.DISTRIBUTECOLOR + Protocol.SPLIT + colour);
    }

    public void sendMakeMove(String msg) {
        send(msg);
        
    }
}
