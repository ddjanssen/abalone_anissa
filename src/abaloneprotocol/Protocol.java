package abaloneprotocol;

/**
 * Protocol for abalone.
 * @author Daan & Pepijn 
 *
 */
public interface Protocol {
    /*
     * V1.3
     */
    
    /**
     * All the arguments should be split by a -.
     */
    public static final String SPLIT = "-";
    
    public static final int SOCKET = 42069;
    
    /**
     * The connectMessage is used by appending the Name to be used by the client.
     * after that, the number of players that the client wants to be in for a game should be appended
     * finally the the extensions the client would like to use should be appended
     * 
     * <p>For example:
     * H-DaanAssies-3-1101
     */
    public static final String CONNECTMESSAGE = "H";    //from client
        
    /**
     * this is for messaging the clients that the game has ended in a draw.
     * to this should be appended a message why & who caused the game to end in a draw
     */
    public static final String DRAWMESSAGE = "D";       //from server, with a string message
    
    
    /**
     * for the serverResponse to the connection request from the client
     * The server name should be appended to this.
     * 
     * <p>For example:
     * HR-DaansServer
     */
    public static final String SERVERRESPONSE = "HR";   //from server
    
    
    /**
     * This is the message for the server to send to all the clients to start the game.
     * 
     */
    public static final String SERVERSTARTGAME = "C";   //from server
    
    /**
     * this is used for the client to confirm the game starting.
     * The name that was used to connect should be used to confirm the client participating in the game
     * 
     * <p>for example:
     * C-DaanAssies
     */
    public static final String CLIENTCONFIRMSTART = "C"; //from client
    
    /**
     * this is used to start the game and give colors to all the clients
     * The server should append the color given to the player.
     * 
     *  <p>For example:
     *  S-CYAN
     * 
     */
    public static final String DISTRIBUTECOLOR = "S"; //from server
    
    /**
     * this is used to make a move.
     * to this should be appended:
     * an array of 3 marbles (using the naive board) (1-61 and 0 for no marble selected in case of 1/2 marbles moved)
     * the direction of which the marble should go (0-5, see the protocol document)
     * 
     * <p>(Optional if the move is a winning move) append W
     * 
     * <p>This message is sent from the client to the server, and then resent by the server to the other client(s)
     */
    public static final String MAKEMOVE = "M"; //from server // from client
    
    
    /**
     * this is used for the client to confirm to the server that the move is legal
     * appended to this should be a 1/0 (1 for legal, 0 for illegal).
     */
    public static final String ACCEPTMOVE = "A"; //from client
    
    /**
     * this is used for the server to confirm the move being made to all the clients who can then execute the move.
     * 
     * <p>appended to this should be:
     * an array of 3 marbles (using the naive board) (1-61 and 0 for no marble selected in case of 1/2 marbles moved)
     * the direction of which the marble should go (0-5, see the protocol document)
     * 
     */
    public static final String CONFIRMMOVE = "MC"; //from server
    
    /**
     * used by the server to indicate that the game has been won
     * the server should append the name of the client who won.
     */
    public static final String WINNER = "W"; //from server
    
    /**
     * used by the server to indicate a person disconnecting.
     * appended should be the name of the disconnected client
     */
    
    public static final String DISCONNECT = "DC"; //from server
    
    /**
     * This is used by the client to send a chat message
     * appended should be the message to be sent.
     * 
     * 
     * <p>the server retransmits this message to everybody with the chat extension enabled
     */
    
    public static final String CHAT = "CHAT";
    
    
}
