package io.castlerush;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import io.castlerush.screens.Play;

public class Client {
    
    private String hostIPAdresse;
    private Play play;
    private Player oppenent;
    private String username;
    private String remoteIP = "";
    private boolean isOpponentOnMap = false, isConnected = false;
    public static int typeOfPlayer;
    public static Socket socket;
    public static DataOutputStream dOut;
    
    public void joinGame(final String ip) throws IOException {
        
        // Bekomme Informationen des Gegners
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    typeOfPlayer = 0;
                    Socket socket = new Socket(remoteIP, 1337);
                    dOut = new DataOutputStream(socket.getOutputStream());
                    dOut.writeByte(100);
                    dOut.flush();

                    InputStream inputStream = socket.getInputStream();
                    DataInputStream dIn = new DataInputStream(inputStream);

                    while (true) {

                        byte messageType = dIn.readByte();

                        switch (messageType) {
                        case 0: // Type W
                            System.out.println("0");
                            play.oppenents.get(0).walk(0);

                            break;
                        case 1: // Type A
                            System.out.println("1");
                            play.oppenents.get(0).walk(1);
                            break;
                        case 2: // Type S
                            System.out.println("2");
                            play.oppenents.get(0).walk(2);
                            break;
                        case 3: // Type D
                            System.out.println("3");
                            play.oppenents.get(0).walk(3);
                            break;
                        case 100: // Create Opponent
                            System.out.println("ist beigetreten!");
                            // play.createPlayer();
                            play.oppenents.add(play.opponent);
                            break;
                        case 101: // Set Spawnpoint
                            System.out.println("101");
                            play.oppenents.get(0).setPosition(dIn.readFloat(), dIn.readFloat());
                            break;
                        default:
                            System.out.println("default");
                        }

                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }).start();
    }

}
