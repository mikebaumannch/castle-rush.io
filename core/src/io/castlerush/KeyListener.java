package io.castlerush;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
    public boolean keyPressed = false;

    public KeyListener(Player player, TiledMap map) {
        this.player = player;
        this.map = map;
        Gdx.input.setInputProcessor(this);
    }

    public boolean checkCollision() {

        TiledMapTileLayer collisionObjectLayer = (TiledMapTileLayer)map.getLayers().get("Water");
        List<Rectangle> tiles = new ArrayList<Rectangle>();
        
        for(int row = 0; row < collisionObjectLayer.getWidth(); row++) {
            for(int col = 0; col < collisionObjectLayer.getHeight(); col++) {
                
                tiles.add(new Rectangle(row*16, col*16, 16, 16));
                
            }
        }
        
        for(Rectangle tile : tiles) {
            if(player.getBoundingRectangle().overlaps(tile)) {
                // collision happened
                System.out.println("STOP BISH");
                return true;
            }else {
                System.out.println("GO BISH");
                return false;
            }
        }
        
//        for (MapObject obj : objects) {
//            
//            System.out.println(obj);
//
//            Rectangle rectangle = ((RectangleMapObject) obj).getRectangle();
//            if (Intersector.overlaps(rectangle, player.getBoundingRectangle())) {
//                // collision happened
//                System.out.println("lol");
//                return true;
//            }else {
//                System.out.println("go");
//                return false;
//            }
//        }
        
        return false;
    }

    public void handleInput() {
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
