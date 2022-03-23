package server.Networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ch.qos.logback.core.net.server.ServerListener;
import commons.models.Player;
import server.api.GameController;

public class ServerStream {
    private static final int PORT =  9001;

    private GameController gameController;
    private ServerListener serverListener;

    private ArrayList<Player> players;
    private ArrayList<ObjectOutputStream> playerwriters;

    public ServerStream(GameController gameController) {
        this.gameController = gameController;
        this.players = new ArrayList<Player>();

        this.playerwriters = new ArrayList<ObjectOutputStream>();


        try {
            this.serverListener = new ServerListener(PORT);
            this.serverListener.start();

        } catch (IOException e) {
            System.out.println("Server: ServerSocket creation failed");

        }
    }

    private class ServerListener extends Thread{
        private ServerSocket listener;

        //boolean for stopping the thread
        private boolean exit = false;

        public ServerListener(int port) throws IOException{

            this.listener = new ServerSocket(port);
            System.out.println("server is know listening with websockets");
        }

        @Override
        public void run() {

            try {

                while (!exit) {
                    new Handler(this.listener.accept()).start();
                }

            } catch (SocketException e) {
                System.out.println("Server (" + this.getId() + "): " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Server: error while trying to accept a new connection");
                e.printStackTrace();
            }
        }

        public void closeSocket(){
            //stop the thread
            //this will terminate the while loop in the thread
            this.exit = true;
        }
    }

    private class Handles extends Thread{
        private Socket socket;
        private InputStream is;
        private ObjectInputStream input;
        private OutputStream os;
        private ObjectOutputStream output;
        
    }




}


