package io.castlerush;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.FloatBuffer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.net.SocketHints;
import io.castlerush.screens.Play;
import io.castlerush.structures.StructureCastle;
import io.castlerush.structures.StructureLoader;

public class Server {

    private String hostIPAdresse;
    private Play play;
    private Player oppenent;
    private float testX;
    private String username;
    private String remoteIP = "";
    public static boolean isOpponentOnMap = false;
    public static int typeOfPlayer = -1;
    public static Socket socket;
    public static DataOutputStream dOut;

    public Server(String hostIPAdresse, Play play, String username) {
        this.hostIPAdresse = hostIPAdresse;
        this.username = username;
        this.play = play;
    }

    public void createGame() {
        typeOfPlayer = 0;

        // Bekomme Informationen des Gegners
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    ServerSocket ss = new ServerSocket(1337);

                    socket = ss.accept();
                    System.out.println(socket.getInetAddress());

                    if (socket != null) {
                        dOut = new DataOutputStream(socket.getOutputStream());
                    }

                    InputStream inputStream = socket.getInputStream();
                    DataInputStream dIn = new DataInputStream(inputStream);

                    while (true) {

                        byte messageType = dIn.readByte();

                        switch (messageType) {
                        case 5:
                            System.out.println("Position");
                            play.opponent.setPosition(dIn.readFloat(), dIn.readFloat());
                            break;
                        case 10:
                            play.player.setHealth(dIn.readInt());
                            break;
                        case 100: // Create Opponent
                            System.out.println("ist beigetreten!");
                            // play.createPlayer();
                            play.oppenents.add(play.opponent);
                            // Gegner Koordinaten zurückschicken
                            isOpponentOnMap = true;
                            dOut.writeByte(100);
                            dOut.writeFloat(play.player.getX());
                            dOut.writeFloat(play.player.getY());
                            dOut.writeFloat(play.castle.getX());
                            dOut.writeFloat(play.castle.getY());
                            dOut.flush();
                            break;
                        case 101: // Setup opponent
                            isOpponentOnMap = true;
                            play.opponent.setPosition(dIn.readFloat(), dIn.readFloat());
                            play.opponentCastle.setPosition(dIn.readFloat(), dIn.readFloat());
                            break;
                            
                        case 102: // Respawn
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
