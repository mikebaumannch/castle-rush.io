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

                            // Gegner Koordinaten zurückschicken
                            dOut.writeByte(100);
                            dOut.writeFloat(play.player.getX());
                            dOut.writeFloat(play.player.getY());
                            dOut.flush();
                            break;
                        case 101: // Set Spawnpoint
                            System.out.println("101");
                            play.oppenents.get(0).setPosition(dIn.readFloat(), dIn.readFloat());
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
