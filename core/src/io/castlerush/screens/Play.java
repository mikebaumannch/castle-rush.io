package io.castlerush.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;

import java.util.concurrent.ThreadLocalRandom;
import io.castlerush.KeyListener;
import io.castlerush.Player;

public class Play implements Screen {

    // PLayer erstellen
    private Player player;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private KeyListener keyListener;
    private String username;
    private TextButtonStyle textButtonStyle;
    private Stage stage;
    private Skin mySkin;

    public Play(String username) {
        this.username = username;
    }
    
    @Override
    public void show() {
        map = new TmxMapLoader().load("maps/maps.tmx");

        renderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.zoom = 1 / 3f;

        player = new Player("Markus", (new Sprite(new Texture("img/player_left.png"))), 100, 100, true,
                (TiledMapTileLayer) map.getLayers().get(0));
        keyListener = new KeyListener(player, map);
        

        int randomX = ThreadLocalRandom.current().nextInt(16, (map.getProperties().get("width", Integer.class) - 3) * 16);
        int randomY = ThreadLocalRandom.current().nextInt(16, (map.getProperties().get("height", Integer.class) - 3) * 16);
        
        player.setX(randomX);
        player.setY(randomY);

        //Informationen
        
        stage = new Stage();

        //Label title
        mySkin = new Skin(Gdx.files.internal("skins/glassy-ui.json"));
        Label gameTitle = new Label("Name: "+username+"\nCoins: "+player.coins, mySkin);
        gameTitle.setSize(100, 100);
        gameTitle.setPosition(20, Gdx.graphics.getHeight() - gameTitle.getHeight()/2 - 30);
        gameTitle.setAlignment(Align.left);
        BitmapFont font = new BitmapFont();
        
        //Button Spiel beenden
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        TextButton buttonExit = new TextButton("Spiel beenden", mySkin, "small");
        buttonExit.setPosition(Gdx.graphics.getWidth() - 20 - buttonExit.getWidth(), Gdx.graphics.getHeight() - gameTitle.getHeight()/2 - 20);
        
        //Itembar anzeigen
        Texture itembar = new Texture(Gdx.files.internal("img/itembar.png"));
        Image item = new Image(itembar);
        item.setSize(itembar.getWidth(),itembar.getHeight());
        item.setPosition(Gdx.graphics.getWidth()/2-item.getWidth()/2, 0);
        
        //Herz für die Lebenspunkte hinzufügen
        Texture texture = new Texture(Gdx.files.internal("img/heart.png"));
        Image heart = new Image(texture);
        heart.setSize(texture.getWidth()/6,texture.getHeight()/6);
        heart.setPosition(Gdx.graphics.getWidth()-heart.getWidth()/2 - 20, 20);
        
        //Anzeige der Lebenspunkte hinzufügen
        mySkin = new Skin(Gdx.files.internal("skins/glassy-ui.json"));
        Label heartTitle = new Label(""+player.getHealth(), mySkin);
        heartTitle.setSize(100, 100);
        heartTitle.setPosition(Gdx.graphics.getWidth()-heart.getWidth()/2 - heart.getWidth() - 20, 0);
        heartTitle.setAlignment(Align.left);
        BitmapFont font1 = new BitmapFont();
       
        //Add Actor
        stage.addActor(gameTitle);
        stage.addActor(heart);
        stage.addActor(heartTitle);
        stage.addActor(item);
        
        //Listener
        buttonExit.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {     
                System.exit(0);
            }
        });
        
        stage.addActor(buttonExit);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(100 / 255f, 155 / 255f, 255 / 255f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        keyListener.handleInput();

        camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);        
        camera.update();

        renderer.setView(camera);
        renderer.render();

        player.setSize((map.getProperties().get("tilewidth", Integer.class)) * 2, map.getProperties().get("tileheight", Integer.class) * 2);  
        
        renderer.getBatch().begin();
        player.draw(renderer.getBatch());
        renderer.getBatch().end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
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
        dispose();

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();

    }

}
