package io.castlerush;

import java.io.Serializable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.castlerush.gui.Shop;
import io.castlerush.items.Item;
import io.castlerush.screens.Play;
import io.castlerush.structures.Structure;
import io.castlerush.structures.StructureLoader;

public class KeyListener extends ClickListener implements InputProcessor, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Play play;
    private Player player;
    private TiledMap map;

    public boolean keyPressed = false;
    public boolean isTouched = false;

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
            if (structure.getName().equals("Fallgrube")) {
                if (Intersector.overlaps(rect, player.getBoundingRectangle())) {
                    return "TRAP";
                }
            }

            /*
             * if (Intersector.overlaps(rect, player.getBoundingRectangle())) { return
             * "TOP"; }
             */
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

    public void handleInput() {

        if (keyPressed) {
            try {

                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)
                        || Gdx.input.isKeyPressed(Input.Keys.A)) {

                    player.walk(0);
                    if (Server.typeOfPlayer == 0) {
                        Server.dOut.writeByte(0);
                        Server.dOut.flush();
                    } else {
                        Client.dOut.writeByte(0);
                        Client.dOut.flush();
                    }
                }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                        || Gdx.input.isKeyPressed(Input.Keys.D)) {
                    player.walk(1);
                    if (Server.typeOfPlayer == 0) {
                        Server.dOut.writeByte(1);
                        Server.dOut.flush();
                    } else {
                        Client.dOut.writeByte(1);
                        Client.dOut.flush();
                    }
                }

                if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
                    player.walk(2);
                    if (Server.typeOfPlayer == 0) {
                        Server.dOut.writeByte(2);
                        Server.dOut.flush();
                    } else {
                        Client.dOut.writeByte(2);
                        Client.dOut.flush();
                    }
                }
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN)
                        || Gdx.input.isKeyPressed(Input.Keys.S)) {
                    player.walk(3);
                    if (Server.typeOfPlayer == 0) {
                        Server.dOut.writeByte(3);
                        Server.dOut.flush();
                    } else {
                        Client.dOut.writeByte(3);
                        Client.dOut.flush();
                    }
                }
                if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                    System.exit(0);

                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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

        if (keycode == Input.Keys.E && !play.shopIsOpen) {
            play.shopIsOpen = false;
            Shop.showShop();
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
        Item[] inv = player.getInventory();
        Item selectedItem = inv[play.getSelectedItem()];

        if (selectedItem != null) {
            if (player.getNumberOfItems()[0] > 0) {
                if (play.getSelectedItem() == 0) {
                    selectedItem.useItem(player);
                }
            } else if (player.getNumberOfItems()[1] > 0) {
                if (play.getSelectedItem() == 1) {
                    selectedItem.useItem(player);
                }
            } else if (play.getSelectedItem() == 2) {
                if (player.getNumberOfItems()[2] > 0) {
                    player.placeStructure(new StructureLoader().towerLvl1);
                    player.increaseNumberOfItems(2, -1);
                }
            } else if (play.getSelectedItem() == 3) {
                if (player.getNumberOfItems()[3] > 0) {
                    player.placeStructure(new StructureLoader().woodWall);
                    player.increaseNumberOfItems(3, -1);
                }

            } else if (play.getSelectedItem() == 4) {
                if (player.getNumberOfItems()[4] > 0) {
                    player.placeStructure(new StructureLoader().trap);
                    player.increaseNumberOfItems(4, -1);
                }
            }
        }
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
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if (amount == -1) {
            // Verschiebe ItemSelector um 1 nach links
            if (play.selectField.getX() <= play.tableInventory.getX()) {
                play.selectField.setPosition(play.selectField.getX() + 240, 0);
                play.setSelectedItem(4);
                return true;
            } else {
                play.selectField.setPosition(play.selectField.getX() - 60, 0);
                play.setSelectedItem(play.getSelectedItem() - 1);
                return true;
            }
        } else {
            // Verschiebe ItemSelector um 1 nach rechts
            if (play.selectField.getX() >= play.tableInventory.getX() + 240) {
                play.selectField.setPosition(play.selectField.getX() - 240, 0);
                play.setSelectedItem(0);
                return true;
            } else {
                play.selectField.setPosition(play.selectField.getX() + 60, 0);
                play.setSelectedItem(play.getSelectedItem() + 1);
                return true;
            }
        }

    }
}
