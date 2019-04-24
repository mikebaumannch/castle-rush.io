package io.castlerush;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

import io.castlerush.Player;

public class KeyListener implements InputProcessor {

    private Player player;
    private TiledMap map;
    private List<Rectangle> tiles;

    public boolean keyPressed = false;

    public KeyListener(Player player, TiledMap map) {
        this.player = player;
        this.map = map;
        Gdx.input.setInputProcessor(this);

        TiledMapTileLayer collisionObjectLayer = (TiledMapTileLayer) map.getLayers().get("Water");
        tiles = new ArrayList<Rectangle>();

        for (int row = 0; row < collisionObjectLayer.getWidth(); row++) {
            for (int col = 0; col < collisionObjectLayer.getHeight(); col++) {

                tiles.add(new Rectangle(row * 16, col * 16, 16, 16));

            }
        }
        System.out.println(tiles.size());

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

        /*
        for (Rectangle tile : tiles) {
            if (playerRect.overlaps(tile)) {
                System.out.println("WTF >:(");
                return true;
            } else {
                System.out.println(":)");
                return false;
            }
        }*/

        return false;
    }

    //
    public void handleInput() {
        if (keyPressed) {

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                player.walk(0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.walk(1);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                player.walk(2);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                player.walk(3);
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
