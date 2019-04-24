package io.castlerush.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import io.castlerush.Player;

public class KeyListener implements InputProcessor {

    private Player player;
    private TiledMap map;
    public boolean keyPressed = false;

    public KeyListener(Player player, TiledMap map) {
        this.player = player;
        this.map = map;
        Gdx.input.setInputProcessor(this);
    }

    public boolean checkCollision() {

        TiledMapTileLayer collisionObjectLayer = (TiledMapTileLayer)map.getLayers().get("Water");
        MapObjects objects = collisionObjectLayer.getObjects();

        // there are several other types, Rectangle is probably the most common one
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            
            System.out.println(rectangleObject.getName());

            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, player.getBoundingRectangle())) {
                // collision happened
                
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    void handleInput() {
        if (keyPressed) {
            if (!checkCollision()) {
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    player.translate(-1, 0);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    player.translate(1, 0);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    player.translate(0, 1);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    player.translate(0, -1);
                }
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
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
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

}
