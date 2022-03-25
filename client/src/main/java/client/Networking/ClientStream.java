package client.Networking;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

import client.scenes.MainCtrl;
import client.scenes.MultiPlayer;
import commons.models.Message;
import commons.models.MessageType;


public class ClientStream {
    private MainCtrl mainCtrl;

    private MultiPlayer multiplayer;

    private ClientListener clientListener;

    private String nickname;

    private OutputStream os;
    private ObjectOutputStream output;
    private InputStream is;
    private ObjectInputStream input;

    /** Construcotr for the clientstream class
     * @param mainCtrl
     * @param multiplayer
     * @param address
     * @param port
     * @param nickname
     */
    public ClientStream(MainCtrl mainCtrl, MultiPlayer multiplayer, String address, int port, String nickname){
        this.mainCtrl = mainCtrl;
        this.nickname = nickname;
        this.multiplayer = multiplayer;

        this.clientListener = new ClientListener(address, port);
        this.clientListener.start();

    }

    private class ClientListener extends Thread{
        private Socket socket;
        private String address;
        private int port;

        private ClientListener(String addres, int port){
            this.address = address;
            this.port = port;
        }

        @Override
        public void run(){
            System.out.println("Client running for player: " + nickname);

            try{
                this.socket = new Socket(address, port);

                os = this.socket.getOutputStream();
                output = new ObjectOutputStream(os);
                is = this.socket.getInputStream();
                input = new ObjectInputStream(is);

                Message msg = new Message(MessageType.CONNECT, nickname, "");
                output.writeObject(msg);

                while(this.socket.isConnected()){

                    Message incomingMsg = (Message) input.readObject();
                    if(incomingMsg !=  null){

                        System.out.println("Client (" + this.getId() + "): received " + incomingMsg.toString());
//                        switch(incomingMsg.getMsgType()){
//                            case GAME_WAITING:
//
//                        }
                    }
                }

            }catch(SocketException e) {
                System.out.println("Socket exception");
                if(e instanceof ConnectException)
                {
                    //do some controller shit
                    System.out.println("connection failed");
                }
                else if(e.getMessage().equals("Connection reset"))
                {
                    System.out.println("Stream closed");
                }
                else e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Stream closed");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }


    }

    /**
     * @param message Send mesasge in stream
     */
    private void sendMessage(Message message)
    {
        try {
            this.output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
