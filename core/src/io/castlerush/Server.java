package io.castlerush;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
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
    private boolean isOpponentOnMap = false;
    private boolean isConnected = false;
    public static int typeOfPlayer;
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

                ServerSocket ss;
                Socket socket = null;

                try {
                    ss = new ServerSocket(1337);
                    socket = ss.accept();
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
                        case 100: //Create Opponent
                            System.out.println("ist beigetreten!");
                            //play.createPlayer();
                            play.oppenents.add(play.opponent);
                            break;
                        case 101: //Set Spawnpoint
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

    private void checkPosition(String ip) {

        try {
            // Sende Informationen an Gegner
            Socket socket = new Socket(remoteIP, 1337);
            OutputStream outputStream = socket.getOutputStream();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(play.player.getX());
        } catch (IOException e) {

        }
    }

    public void joinGame(final String ip) throws IOException {
        typeOfPlayer = 1;
        socket = new Socket(ip, 1337);
        dOut = new DataOutputStream(socket.getOutputStream());
        dOut.writeByte(100);
        dOut.flush();
    }
}
