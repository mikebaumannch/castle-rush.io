package io.castlerush.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class Menu implements Screen{
    
    
    private Game game;
    private Stage stage;
    private TextButton button;
    private TextButtonStyle textButtonStyle;
    private BitmapFont font;
    private Skin mySkin;
    private TextField txtUsername;
    private TextButton buttonExit;
    
    public Menu(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        
        stage = new Stage();

        //Label title
        Gdx.input.setInputProcessor(stage);
        mySkin = new Skin(Gdx.files.internal("skins/glassy-ui.json"));
        Label gameTitle = new Label("CastleRush.io", mySkin, "big");
        gameTitle.setSize(100, 100);
        gameTitle.setPosition(Gdx.graphics.getWidth()/2 - gameTitle.getWidth()/2, Gdx.graphics.getWidth()/2 - gameTitle.getWidth()/2);
        gameTitle.setAlignment(Align.center);
        font = new BitmapFont();
        
        //User Input Field
        txtUsername = new TextField("", mySkin);
        txtUsername.setMessageText("Benutzername...");
        txtUsername.setWidth(400);
        txtUsername.setPosition(Gdx.graphics.getWidth()/2 - txtUsername.getWidth()/2, Gdx.graphics.getHeight()/2 - txtUsername.getHeight()/2 + 80);
        
        //Button Spiel starten
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        button = new TextButton("Spiel starten", mySkin, "small");
        button.setPosition(Gdx.graphics.getWidth()/2 - button.getWidth()/2, Gdx.graphics.getHeight()/2 - button.getHeight()/2);
        
        //Button Spiel beenden
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        buttonExit = new TextButton("Spiel beenden", mySkin, "small");
        buttonExit.setPosition(Gdx.graphics.getWidth()/2 - buttonExit.getWidth()/2, Gdx.graphics.getHeight()/2 - buttonExit.getHeight()/2 - 80);
        
        //Actor hinzufügen
        stage.addActor(gameTitle);
        stage.addActor(txtUsername);
                
        //Listener
        button.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {     
                //Speichere Eingabe
                String username = txtUsername.getText();
                
                //Starte Spiel
                game.setScreen(new Play(username));
            }
        });
        
        buttonExit.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {     
                System.exit(0);
            }
        });
        
        stage.addActor(button);
        stage.addActor(buttonExit);
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
