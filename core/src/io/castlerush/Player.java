package io.castlerush;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import io.castlerush.items.Item;
import io.castlerush.screens.Play;
import io.castlerush.structures.Structure;

public class Player extends Sprite {

    // Player information
    private String name;
    private int coins;
    public int health;
    public boolean isCastleAlive = true;
    private Vector2 velocity = new Vector2();
    private float speed = 100, delta;
    private List<Item> inventory = new ArrayList<Item>();

    // Util
    private Play play;
    public TiledMap map;
    public KeyListener keyListener;

    public Player(String name, Sprite skin, int coins, int health, boolean isCastleAlive,
            TiledMap map, Play play) {

        super(skin);
        this.play = play;
        this.name = name;
        this.coins = coins;
        this.health = health;
        this.isCastleAlive = isCastleAlive;
        this.map = map;
        this.keyListener = new KeyListener(this, map, play);
    }

    public void placeStructure(Structure structure) {
        structure.setBounds(this.getX(), this.getY(),
                (map.getProperties().get("tilewidth", Integer.class)) * 8,
                (map.getProperties().get("tilewidth", Integer.class)) * 8);
        play.structuresOnMap.add(structure);
    }

    public void earnCoins() {
        if (isCastleAlive) {
            coins += 10000;
        }
    }
    
    private void loadItemsInInventory() {
        
        for (Item item : inventory) {
            
        }
    }

    public void buy(Item item) {
        
        coins -= item.getPrice();
        
        if (item.getName().equals("Holzschwert") || item.getName().equals("Steinwert")
                || item.getName().equals("Eisenschwert")) {
            play.weaponImageSlot0
                    .setDrawable(new TextureRegionDrawable(new TextureRegion(item.getTexture())));
        } else if (item.getName().equals("Steinschleuder") || item.getName().equals("Bogen")) {
            play.transparentImageSlot1
                    .setDrawable(new TextureRegionDrawable(new TextureRegion(item.getTexture())));
        } else if (item.getName().equals("Bogenschützenturm")
                || item.getName().equals("Kanonenturm")) {
            play.transparentImageSlot2
                    .setDrawable(new TextureRegionDrawable(new TextureRegion(item.getTexture())));
        } else if (item.getName().equals("Holzmauer") || item.getName().equals("Steinmauer")) {
            play.transparentImageSlot3
                    .setDrawable(new TextureRegionDrawable(new TextureRegion(item.getTexture())));
        } else if (item.getName().equals("Fallgrube")) {
            play.transparentImageSlot4.setDrawable(new TextureRegionDrawable(new TextureRegion(item.getTexture())));
        }
        
        inventory.add(item);

    }

    public void walk(int key) {

        switch (key) {

        // walking left
        case 0:
            if (!keyListener.checkCollision().equals("LEFT")) {
                setX(getX() + velocity.x * delta);
            }
            break;

        // walking right
        case 1:
            if (!keyListener.checkCollision().equals("RIGHT")) {
                setX(getX() + -velocity.x * delta);
            }
            break;

        // walking up
        case 2:
            if (!keyListener.checkCollision().equals("TOP")) {
                setY(getY() + -velocity.y * delta);
            }
            break;

        // walking down
        case 3:
            if (!keyListener.checkCollision().equals("BOTTOM")) {
                setY(getY() + velocity.y * delta);
            }
            break;
        }

        if (keyListener.checkCollision().equals("COIN")) {
            this.coins += 5;
        }

    }

    @Override
    public void draw(Batch spriteBatch) {

        // Zeitspanne zwischen aktuellem und vorherigem Frame
        delta = Gdx.graphics.getDeltaTime();
        update(delta);
        super.draw(spriteBatch);
    }

    private void update(float delta) {

        // Apply speed
        velocity.y -= speed * delta;
        velocity.x -= speed * delta;

        // Clamp velocity (Beschleunigung einschränken)
        if (velocity.y > speed) {
            velocity.y = speed;
        } else if (velocity.y < speed) {
            velocity.y = -speed;
        }

        if (velocity.x > speed) {
            velocity.x = speed;
        } else if (velocity.x < speed) {
            velocity.x = -speed;
        }

        keyListener.handleInput();
        
        // Updates inventory
        loadItemsInInventory();
    }

    void attack() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }
    
    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }
}
