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
    private boolean isOpponentOnMap = false, isConnected = false;
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
                    while (true) {
                        
                        socket = ss.accept();
                        InputStream inputStream = socket.getInputStream();
                        DataInputStream dIn = new DataInputStream(inputStream);

                        byte messageType = dIn.readByte();

                        switch (messageType) {
                        case 0: // Type W
                            play.oppenents.get(0).walk(0);
                            
                            break;
                        case 1: // Type A
                            play.oppenents.get(0).walk(1);
                            break;
                        case 2: // Type S
                            play.oppenents.get(0).walk(2);
                            break;
                        case 3: // Type D
                            play.oppenents.get(0).walk(3);
                            break;
                        case 100: //Create Opponent
                            play.createPlayer();
                            break;
                        case 101: //Set Spawnpoint
                            play.oppenents.get(0).setPosition(dIn.readFloat(), dIn.readFloat());
                            break;
                        default:

                        }
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }
        }).start();
    }

    public void joinGame(final String ip) throws IOException {
        
        typeOfPlayer = 1;
        socket = new Socket(ip, 1337);
        dOut = new DataOutputStream(socket.getOutputStream());
        dOut.writeByte(100);
        dOut.flush();

    }
}
