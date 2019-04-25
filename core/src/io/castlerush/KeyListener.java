package io.castlerush;

import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.castlerush.Player;
import io.castlerush.gui.Shop;
import io.castlerush.items.ItemLoader;
import io.castlerush.screens.Play;
import io.castlerush.structures.Structure;
import io.castlerush.structures.StructureLoader;

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
        this.play = play;
        this.player = player;
        this.map = map;
    }

    public String checkCollision() {

        MapLayer collisionObjectLayer = map.getLayers().get("WaterCollider");
        MapObjects objects = collisionObjectLayer.getObjects();

        objects.getCount();

        // Finds every rectangle in the object layer (made in Tiled)
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, player.getBoundingRectangle())) {
                // collision happened
                if (rectangleObject.getName().equals("Top")) {
                    return "TOP";
                } else if (rectangleObject.getName().equals("Bottom")) {
                    return "BOTTOM";
                } else if (rectangleObject.getName().equals("Left")) {
                    return "LEFT";
                } else {
                    return "RIGHT";
                }
            }
        }

        for (Structure structure : play.structuresOnMap) {

            Rectangle rect = structure.getBoundingRectangle();
            if (Intersector.overlaps(rect, player.getBoundingRectangle())) {
                return "TOP";
            }
        }

        for (Structure coin : play.coins) {

            Rectangle rect = coin.getBoundingRectangle();
            if (Intersector.overlaps(rect, player.getBoundingRectangle())) {
                play.coins.remove(coin);
                return "COIN";
            }
        }

        return "NO";
    }

    //
    public void handleInput() {
        if (keyPressed) {

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                player.walk(0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                player.walk(1);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
                player.walk(2);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
                player.walk(3);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                System.exit(0);
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

        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            if (direction == "LEFT") {
                player.flip(true, false);
                direction = "RIGHT";
            }
        }

        else if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            if (direction == "RIGHT") {
                player.flip(true, false);
                direction = "LEFT";
            }
        }

        if (keycode == Input.Keys.E) {

            // Shop �ffnen
            if (!play.shopIsOpen) {
                Shop.showShop();
            }
        }

        if (keycode == Input.Keys.Q) {
            player.placeStructure(new StructureLoader().castleLvl1);
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

}
