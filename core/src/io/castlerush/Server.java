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

    public Server(String hostIPAdresse, Play play, String username) {
        this.hostIPAdresse = hostIPAdresse;
        this.username = username;
        this.play = play;
    }
    
    public void createGame() {
        //Bekomme Informationen des Gegners
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
                        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                        try {
                            oppenent = (Player) objectInputStream.readObject();
                          //Wenn Gegner nicht erstellt wurde, dann Spieler erstellen
                            if(!isOpponentOnMap) {
                                play.createPlayer(oppenent.getName());
                                     
                                isOpponentOnMap = true;
                            }
                            else {
                                //Gegner aktualisieren                    
                                play.oppenents.get(0).setPosition(testX, 0);
                            }               
                                            
                            //IP-Adresse des Gegners herausfinden
                            InetSocketAddress sockaddr = (InetSocketAddress)socket.getRemoteSocketAddress();
                            InetAddress inaddr = sockaddr.getAddress();
                            Inet4Address in4addr = (Inet4Address)inaddr;
                            remoteIP = in4addr.getHostAddress();
                            
                            if(remoteIP.length() > 0 & !isConnected) {
                                checkPosition(remoteIP);
                                isConnected = true;
                            }
                        } catch (ClassNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
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
      //Sende Informationen an Gegner
        Gdx.app.postRunnable(new Runnable(){
            @Override
            public void run() {
                
                while(true) {
                    
                    try {
                        Socket socket = new Socket(remoteIP, 1337);
                        OutputStream outputStream = socket.getOutputStream();
            
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                        objectOutputStream.writeObject(play.player);
                        
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    
    public void joinGame(final String ip) throws IOException {
        
        Gdx.app.postRunnable(new Runnable(){
            @Override
            public void run() {
                while(true) {
                    
                    try {
                        Socket socket = new Socket(ip, 1337);
                        OutputStream outputStream = socket.getOutputStream();
            
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                        objectOutputStream.writeObject(play.player);
                        
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
