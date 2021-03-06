package io.castlerush;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

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
                        case 5:
                            System.out.println("Position");
                            play.opponent.setPosition(dIn.readFloat(), dIn.readFloat());
                            break;
                        case 10:
                            System.out.println("Schaden: ");
                            play.player.setHealth(dIn.readInt());
                            break;
                        case 11:
                            System.out.println("Case 11");
                            if (play.castle != null) {
                                play.castle.setHealth(dIn.readInt());
                            }
                            break;
                        case 12: // Castle death
                            play.player.isCastleAlive = false;
                            play.castle = null;
                            break;
                        case 100: // Create Opponent
                            play.opponent.setPosition(dIn.readFloat(), dIn.readFloat());
                            play.opponentCastle.setPosition(dIn.readFloat(), dIn.readFloat());
                            isOpponentOnMap = true;
                            break;
                        case 101: // Set opponent position
                            play.opponent.setPosition(dIn.readFloat(), dIn.readFloat());
                            play.opponent.setHealth(dIn.readInt());
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
