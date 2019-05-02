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
    private String username;
    private String remoteIP = "";
    private boolean isOpponentOnMap = false, isConnected = false;
    public static int typeOfPlayer;
    public static Socket socket;
    public static DataOutputStream dOut;

    public Client(String hostIPAdresse, Play play, String username) {
        this.hostIPAdresse = hostIPAdresse;
        this.username = username;
        this.play = play;
    }

    public void joinGame(final String ip) throws IOException {

        // Bekomme Informationen des Gegners
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    typeOfPlayer = 1;
                    socket = new Socket(ip, 1337);
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
                            play.opponent.walk(0);
                            break;
                        case 1: // Type A
                            System.out.println("1");
                            play.opponent.walk(1);
                            break;
                        case 2: // Type S
                            System.out.println("2");
                            play.opponent.walk(2);
                            break;
                        case 3: // Type D
                            System.out.println("3");
                            play.opponent.walk(3);
                            break;
                        case 100: // Create Opponent
                            float x = dIn.readFloat();
                            float y = dIn.readFloat();
                            play.opponent.setPosition(x, y);
                            isOpponentOnMap = true;
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
