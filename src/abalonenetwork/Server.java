package abalonenetwork;

import abalonecontrol.ServerGame;
import abaloneview.ServerTui;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Server implements Runnable {

    private ServerSocket serverSocket;
    private Map<Integer, ArrayList<ClientHandler>> queues = new HashMap<>();
    private ArrayList<ClientHandler> handlers = new ArrayList<>();

    /**
     * creates a new instance of Server.
     * @param serverSocket - Serversocket of the server
     */
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        queues.put(2, new ArrayList<>());
        queues.put(3, new ArrayList<>());
        queues.put(4, new ArrayList<>());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();         
                //hier wordt een bij een nieuw bericht een clienthandler aangemaakt
                ClientHandler handler = new ClientHandler(this, socket);
                handler.start();
                handlers.add(handler);
            } catch (IOException e) {
                e.printStackTrace();
            }  
        }
    }
    
    /**
     * returns if there exists already a handler with the same name.
     * @param name - name of handler
     */
    public boolean handlerNameAlreadyUsed(String name) {
        for (ClientHandler handler : handlers) {
            if (handler.getHandlerName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * add players to the queue of the preferred game.
     * @param numberOfPlayers - preferred number of players
     * @param clienthandler - clienthander of the client
     */
    public synchronized void addToQueue(int numberOfPlayers, ClientHandler clienthandler) {
        if (!queues.get(numberOfPlayers).contains(clienthandler)) { 
            // for security reasons otherwise same person plays as different players
            System.out.println(" > Player: " + clienthandler.getHandlerName() + " is added to the queue for " + numberOfPlayers + " players.");
            queues.get(numberOfPlayers).add(clienthandler);
            refreshQueues();
        }
    }

    private void refreshQueues() {
        synchronized (queues) {
            for (int nrOfPlayers : queues.keySet()) {
                if (queues.get(nrOfPlayers).size() == nrOfPlayers) {
                    startGame(queues.get(nrOfPlayers));
                    queues.put(nrOfPlayers, new ArrayList<>());
                }
            }
        }
    }

    private void startGame(ArrayList<ClientHandler> clienthandlers) {
        String[] playernames = new String[clienthandlers.size()];
        for (int i = 0; i < playernames.length; i++) {
            playernames[i] = clienthandlers.get(i).getHandlerName();
        }
        ServerGame game = new ServerGame(playernames, new ServerTui(), clienthandlers);
        game.start();
        
    }


    public String getName() {
        return "AnissaServer";
    }

}
