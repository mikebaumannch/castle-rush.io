package io.castlerush;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import io.castlerush.Player;
import io.castlerush.screens.Play;

public class KeyListener extends ClickListener implements InputProcessor {
    
    private Play play;
    private Player player;
    private TiledMap map;
    private List<Rectangle> tiles;
    private Skin mySkin;

    public boolean keyPressed = false;
    private boolean isTouched = false;
    private String direction = "LEFT";

    public KeyListener(Player player, TiledMap map, Play play) {
        this.play=play;
        this.player = player;
        this.map = map;
    }

    public boolean checkCollision() {

        MapLayer collisionObjectLayer = map.getLayers().get("WaterCollider");
        MapObjects objects = collisionObjectLayer.getObjects();

        objects.getCount();

        // there are several other types, Rectangle is probably the most common one
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, player.getBoundingRectangle())) {
                // collision happened
                return true;
            }
        }

        return false;
    }

    //
    public void handleInput() {
        if (keyPressed) {

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                player.walk(0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)|| Gdx.input.isKeyPressed(Input.Keys.D)) {
                player.walk(1);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)|| Gdx.input.isKeyPressed(Input.Keys.W)) {
                player.walk(2);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
                player.walk(3);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                System.exit(0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.E)) {
                //Shop �ffnen
                play.showShop();
            }
        }
        
    }

    @Override
    public boolean keyUp(int keycode) {
        keyPressed = false;
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        keyPressed = true;

        if (keycode == Input.Keys.RIGHT) {
            if (direction == "LEFT") {
                player.flip(true, false);
                direction = "RIGHT";
            }
        }

        else if (keycode == Input.Keys.LEFT) {
            if (direction == "RIGHT") {
                player.flip(true, false);
                direction = "LEFT";
            }
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        isTouched = true;
        
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isTouched = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {

        System.out.println("hi");
        
    }

}
