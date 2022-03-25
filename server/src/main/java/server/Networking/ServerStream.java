
package server.Networking;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.util.ArrayList;


import commons.models.Message;
import commons.models.Player;
import commons.models.MessageType;
import server.api.GameController;

public class ServerStream {
    private static final int PORT =  8080;

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

        private ServerListener(int port) throws IOException{

            this.listener = new ServerSocket(port);
            System.out.println("server is know listening with websockets");
        }

        @Override
        public void run() {

            try {

                while (!exit) {
                    //in the server listener runnable you must start a new threas
                    //because accecpting a socket connection is blocking

                    //forever while since the server can accept and infinite amount of
                    //client socket connections
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

    //when connection between server and client is stablished this is
    //the code that will be executed
    private class Handler extends Thread {
        private Socket socket;
        private InputStream is;
        private ObjectInputStream input;
        private OutputStream os;
        private ObjectOutputStream output;

        private Handler(Socket socket) {
            //we connect a "server socket" with a "client socket"
            //the socket attribute is the server socket connected to the
            //client socket in question
            System.out.println("Server accepted connection");
            this.socket = socket;
        }

        @Override
        public void run() {
            try {

                //since this.socket refers to a server side socket
                // we get the client out put but server input like so

                this.is = this.socket.getInputStream();
                this.input = new ObjectInputStream(this.is);
                this.os = this.socket.getOutputStream();
                this.output = new ObjectOutputStream(this.os);

                while (this.socket.isConnected()) {


                    Message incomingMsg = (Message) this.input.readObject();
                    if (incomingMsg != null) {
                        System.out.println("Server received" + incomingMsg.toString());
                        switch (incomingMsg.getMsgType()) {
                            case CONNECT:

                                Message mreply = new Message();
                                System.out.println("Server: connect message received");
                                Player p = new Player(incomingMsg.getUsername(), this.socket.getInetAddress());
                                players.add(p);
                                playerwriters.add(this.output);

                                //something like
                                //Gamecontroller.addplayer(p);forward

                                //we woudl then like to send a message to all the other players and this  one
                                //about the
                                mreply.setMsgType(MessageType.USER_JOINED);
                                mreply.setNickname(incomingMsg.getUsername());

                                forward(mreply);
                                break;
                            default:
                                break;
                        }



                    }

                }
            } catch (SocketException e) {
                if (e.getMessage().contains("Connection reset")) {
                    System.out.println("Stream closed");
                }
            } catch (IOException e) {
                // if we close the socket when kick/disconnect is received, then:
                // when the server kicks an user, IOException is thrown because the thread which is listening, tries to
                // read from the stream, but the socket has been closed from the other endpoint
                System.out.println("Errore stream ");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void sendMessage(Message message){
        for(int i = 1; i<this.players.size(); i++){
            try{
                this.playerwriters.get(i).writeObject(message);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private void forward(Message msg){
        //forward message to each connected cleint except the one that sent the message first
        for(int i = 1; i<this.players.size(); i++){

            if(!msg.getUsername().equals(this.players.get(i).getNickname())){
                try{
                    this.playerwriters.get(i).writeObject(msg);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }




}



