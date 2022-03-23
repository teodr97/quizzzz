package client.Networking;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import client.scenes.MainCtrl;
import commons.models.Message;
import commons.models.MessageType;

public class ClientStream {
    private MainCtrl mainCtrl;
    private ClientListener clientListener;

    private String nickname;

    private OutputStream os;
    private ObjectOutputStream output;
    private InputStream is;
    private ObjectInputStream input;

    public ClientStream(MainCtrl mainCtrl, String address, int port, String nickname){
        this.mainCtrl = mainCtrl;
        this.nickname = nickname;

        this.clientListener = new ClientListener(address, port);
        this.clientListener.start();

    }

    private class ClientListener extends Thread{
        private Socket socket;
        private String address;
        private int port;

        public ClientListener(String addres, int port){
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
                        switch(incomingMsg.getMsgType()){

                            
                        }
                    }
                }

            }
        }
    }

}
