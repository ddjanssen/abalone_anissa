package abaloneview;

import abalonecontrol.LocalGame;
import abalonenetwork.Client;
import abalonenetwork.Server;
import abaloneprotocol.Protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


/**
 * This is the class that you need to run for the whole game.
 * @author Anissa
 *
 */
public class MenuTui {

    private Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        new MenuTui();
    }

    /**
     * This method prints out the startup menu of Abalone. The player can chose if
     * he wants to play a network game or a local game.
     *
     */
    public MenuTui() {
        System.out.println("Hi, this is the menu of the game Abalone.");
        System.out.println("For a local game type 1, for a network game type 2");
        String gameType = "";
        while (gameType.equals("")) {
            if (input.hasNextLine()) {
                gameType = input.nextLine();
                switch (gameType) {
                    case ("1"): {
                        startLocalGame();
                        break;
                    }
                    case ("2"): {
                        startNetworkGame();
                        break;
                    }
                    default: {
                        gameType = "";
                        System.out.println("Please enter 1 or 2");
                        break;
                        
                    }
                }
            }
        }

    }

    /**
     * asks the player with how many players he wants to play.
     * returns the preference number of players
     * @return
     */
    private int getAmountOfPlayers() {
        int playerAmount = 0;
        String inputAmount = "";
        System.out.println("With how many players would you like to play? 2/3/4");
        while (inputAmount.equals("")) {
            if (input.hasNextLine()) {
                inputAmount = input.nextLine();
                switch (inputAmount) {
                    case ("2"):
                    case ("3"):
                    case ("4"): {
                        playerAmount = Integer.parseInt(inputAmount);
                        break;
                    }
                    default: {
                        inputAmount = "";
                        break;
                    }
                }
            }
        }
        return playerAmount;
    }

    /**
     * starts a network game.
     */
    private void startNetworkGame() {
        System.out.println("Do you want to start a client or a server?");
        System.out.println("For a client game type 1, for a server game type 2");
        String clientServer = "";
        while (clientServer.equals("")) {
            if (input.hasNextLine()) {
                clientServer = input.nextLine();
                switch (clientServer) {
                    case ("1"): {
                        startClient();
                        break;
                    }
                    case ("2"): {
                        startServer();
                        break;
                    }
                    default: {
                        clientServer = "";
                        System.out.println("Please enter 1 or 2");
                        break;
                        
                    }
                }
            }
        }

    }

    /**
     * after starting the network game, starting the server which is the host for the game.
     */
    private void startServer() {
        try {
            Server server = new Server(new ServerSocket(Protocol.SOCKET));
            new Thread(server).start();
        } catch (IOException e) {
            //TODO
        }
    }
    
    /**
     * after starting the network game, starting the client to connect to a different host. 
     */
    private void startClient() {
        String playername = "";
        
        System.out.println("What is your name?");
        while (playername.equals("")) {
            if (input.hasNextLine()) {
                playername = input.nextLine();
                if (playername.split(Protocol.SPLIT).length > 1) {
                    playername = "";
                    System.out.println("You can't have a - in your name");
                } else {
                    System.out.println("Welcome " + playername);
                }
            }
        }
        System.out.println("What is the IP address of the host?");
        Socket socket = null;                           // waarom op null en niet gelijk de protocol dingest
        InetAddress address = null;
        while (address == null) {
            String ip;
            if (input.hasNextLine()) {
                ip = input.nextLine();
                try {
                    address = InetAddress.getByName(ip);
                    socket = new Socket(address, Protocol.SOCKET);
                } catch (IOException e) {
                    System.out.println("IP address is invalid, please try again");
                }
            }
        }
        int numberOfPlayers = getAmountOfPlayers();
        ClientTui tui = new ClientTui(); //TODO
        Client client = new Client(playername, socket, tui, numberOfPlayers);
        new Thread(client).start();
    }

    /** Start a <code>LocalGame</code>.
     * 
     */
    private void startLocalGame() {
        // TODO Auto-generated method stub
        int playerAmount = getAmountOfPlayers();
        String[] playerList = new String[playerAmount];

        for (int i = 0; i < playerAmount; i++) {
            playerList[i] = "";
            System.out.println("Is player " + (i + 1) + " a computer player? y/n");
            String computer = "";
            while (computer.equals("")) {
                if (input.hasNextLine()) {
                    computer = input.nextLine();
                    switch (computer) {
                        case ("n"): {
                            while (playerList[i].equals("")) {
                                System.out.println("Please enter your name");
                                if (input.hasNextLine()) {
                                    playerList[i] = input.nextLine();
                                }
                                if (playerList[i].split(" ").length > 1) {
                                    playerList[i] = "";
                                    System.out.println("You can't have spaces in your name.");
                                } else {
                                    System.out.println("Player " + (i + 1) + ": " + playerList[i]);
                                }
    
                            }
                            break;
                        }
                        case ("y"): {
                            playerList[i] = "Computer " + (i + 1);
                            System.out.println("Player " + (i + 1) + ": " + playerList[i]);
                            break;
                        }
                        default: {
                            computer = "";
                            System.out.println("Please enter y or n");
                            break;
                        }
                    }
                }
            }
        }
        View view = new Tui(playerList, LocalGame.class);
        view.start();
    }
}
