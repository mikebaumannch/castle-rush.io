package io.castlerush.screens;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;

import io.castlerush.Server;
import io.castlerush.items.Item;
import io.castlerush.items.ItemLoader;

public class Menu implements Screen {

    private Play play;
    private Game game;
    private Stage stage;
    private TextButton button;
    private TextButtonStyle textButtonStyle;
    private BitmapFont font;
    private Skin mySkin;
    private TextField txtUsername;
    private TextButton buttonExit;
    private TextButton buttonJoinGame;
    private Dialog dialog;
    private String myIPAdress;

    public Menu(Game game) {
        this.game = game;
        play = new Play();
    }

    @Override
    public void show() {

        stage = new Stage();

        // Label title
        Gdx.input.setInputProcessor(stage);
        mySkin = new Skin(Gdx.files.internal("skins/glassy-ui.json"));
        Label gameTitle = new Label("CastleRush.io", mySkin, "big");
        gameTitle.setSize(100, 100);
        gameTitle.setPosition(Gdx.graphics.getWidth() / 2 - gameTitle.getWidth() / 2,
                Gdx.graphics.getWidth() / 2 - gameTitle.getWidth() / 2);
        gameTitle.setAlignment(Align.center);
        font = new BitmapFont();

        // User Input Field
        txtUsername = new TextField("", mySkin);
        txtUsername.setMessageText("Benutzername...");
        txtUsername.setWidth(400);
        txtUsername.setPosition(Gdx.graphics.getWidth() / 2 - txtUsername.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - txtUsername.getHeight() / 2 + 80);

        // Button Spiel erstellen
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        button = new TextButton("Spiel erstellen", mySkin, "small");
        button.setPosition(Gdx.graphics.getWidth() / 2 - button.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - button.getHeight() / 2);

        // Button Spiel beitreten
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        buttonJoinGame = new TextButton("Spiel beitreten", mySkin, "small");
        buttonJoinGame.setPosition(Gdx.graphics.getWidth() / 2 - buttonJoinGame.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - buttonJoinGame.getHeight() / 2 - 80);

        // Button Spiel beenden
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        buttonExit = new TextButton("Spiel beenden", mySkin, "small");
        buttonExit.setPosition(Gdx.graphics.getWidth() / 2 - buttonExit.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - buttonExit.getHeight() / 2 - 160);

        // Actor hinzufügen
        stage.addActor(gameTitle);
        stage.addActor(txtUsername);

        // Listener
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Speichere Eingabe
                String username = txtUsername.getText();

                // Speichere IP Adresse
                try {
                    myIPAdress = InetAddress.getLocalHost().getHostAddress();

                } catch (UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(myIPAdress);

                // Starte Server
                Server myServer = new Server(myIPAdress, play, username);
                myServer.createGame();

                // Starte Spiel
                game.setScreen(play);
            }
        });

        buttonExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.exit(0);
            }
        });

        buttonJoinGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Speichere Eingabe
                String username = txtUsername.getText();

                // Öffnet Dialog
                createDialogJoin();
                showDialogJoin();
            }
        });

        stage.addActor(button);
        stage.addActor(buttonExit);
        stage.addActor(buttonJoinGame);
    }

    private void createDialogJoin() {
        // Shop dialog
        dialog = new Dialog("", mySkin, "default");

        // Title
        Label title = new Label("Spiel beitreten", mySkin, "big");

        // Informations
        Label enterTargetIP = new Label("IP-Adresse des Zielhosts eingeben:", mySkin);

        // User Input Field
        final TextField txtIP = new TextField("", mySkin);
        txtIP.setMessageText("IPv4-Adresse (z.B: 192.168.1.1)");
        txtIP.setWidth(200);
        txtIP.setPosition(Gdx.graphics.getWidth() / 2 - txtIP.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - txtIP.getHeight() / 2 + 80);

        // Buttons
        TextButton joinButton = new TextButton("Beitreten", mySkin, "small");
        TextButton cancelButton = new TextButton("Abbrechen", mySkin, "small");

        // Create Table
        Table table = new Table();

        // Add Components to table
        table.add(enterTargetIP).width(150);
        table.row();
        table.add(txtIP).width(250).padLeft(100);
        table.row().padTop(150);
        table.add(joinButton).width(150);
        table.row();
        table.add(cancelButton).width(150);

        dialog.hide();
        dialog.setWidth(400);
        dialog.setHeight(500);
        stage.addActor(dialog);

        // Settings
        title.setPosition(dialog.getWidth() / 2 - title.getWidth() / 2, dialog.getHeight() - 100);

        dialog.addActor(table);
        dialog.addActor(title);

        table.setPosition(dialog.getWidth() / 2 - table.getWidth() / 2 - 100, 210);

        joinButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                // Variablen auslesen
                String targetIP = txtIP.getText();
                String username = txtUsername.getText();

                // Starte Server
                Server myServer = new Server(myIPAdress, play, username);
                try {
                    myServer.joinGame(targetIP);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                game.setScreen(play);
            }
        });
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                // Dialog schliessen
                dialog.hide();
            }
        });
    }

    // Zeigt den Beitrittsscreen an
    private void showDialogJoin() {
        dialog.show(stage);
        dialog.setWidth(400);
        dialog.setHeight(500);
        dialog.setPosition(Gdx.graphics.getWidth() / 2 - dialog.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - dialog.getHeight() / 2);
        stage.addActor(dialog);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
