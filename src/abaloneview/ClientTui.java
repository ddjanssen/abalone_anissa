package abaloneview;

import abalonecontrol.LocalGame;

public class ClientTui extends Tui {

    public ClientTui() {
       
        // TODO Auto-generated constructor stub
    }

    public static void printDrawMessage(String[] msgArgs) {
        System.out.println("The game is finished, it's a draw. " + msgArgs);
        
    }

    public void playerDisconnected(String[] msgArgs) {
        System.out.println(msgArgs + " disconnected from the server");
        
    }

    public void serverDisconnected() {
        System.out.println("You are disconnected from the server");
        
    }

}
