package io.castlerush;

import java.io.Serializable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.castlerush.items.Item;
import io.castlerush.screens.Play;
import io.castlerush.structures.Structure;
import io.castlerush.structures.StructureCastle;
import io.castlerush.structures.StructureLoader;

public class Player extends Sprite implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // Player information
    private String name;
    private int coins, health, fallCounter = 0;
    public boolean isCastleAlive = true, isFalling = false;
    private Vector2 velocity = new Vector2();
    private float speed = 100, delta;
    private Item inventory[] = new Item[5];
    private int numberOfItems[] = new int[5];

    // Util
    private Play play;
    public TiledMap map;
    public KeyListener keyListener;
    float fallX;
    float fallY;

    public Player(String name, int coins, int health, boolean isCastleAlive,
            TiledMap map, Play play) {

        super((new Sprite(new Texture("img/player.png"))));
        this.play = play;
        this.name = name;
        this.coins = coins;
        this.health = health;
        this.isCastleAlive = isCastleAlive;
        this.map = map;
        this.keyListener = new KeyListener(this, map, play);
    }

    // Place structures
    public void placeStructure(Structure structure) {
        if (structure.getName().equals("Fallgrube")) {

            structure.setBounds(this.getX(), this.getY() + 50,
                    (map.getProperties().get("tilewidth", Integer.class)) * 2,
                    (map.getProperties().get("tilewidth", Integer.class)) * 2);
            play.structuresOnMap.add(structure);

        } else {

            structure.setBounds(this.getX(), this.getY(),
                    (map.getProperties().get("tilewidth", Integer.class)) * 8,
                    (map.getProperties().get("tilewidth", Integer.class)) * 8);
            play.structuresOnMap.add(structure);
        }
    }

    // Earn coins
    public void earnCoins() {
        if (isCastleAlive) {
            coins += 10000;
        }
    }

    // Upgrade player structures
    public StructureCastle upgrade(StructureCastle structure) {

        StructureCastle castleLvl2 = new StructureLoader().castleLvl2;

        if (structure.getLevel() == 1) {
            return castleLvl2;
        }

        return null;

    }

    // Buy an item
    public void buy(Item item) {

        coins -= item.getPrice();

        if (item.getName().equals("Holzschwert") || item.getName().equals("Steinschwert")
                || item.getName().equals("Eisenschwert")) {
            play.slots[0].setDrawable(new TextureRegionDrawable(item.getTexture()));
            inventory[0] = item;
            numberOfItems[0]++;

        } else if (item.getName().equals("Steinschleuder") || item.getName().equals("Bogen")) {
            play.slots[1].setDrawable(new TextureRegionDrawable(item.getTexture()));
            inventory[1] = item;
            numberOfItems[1]++;
        } else if (item.getName().equals("Bogenschützenturm")
                || item.getName().equals("Kanonenturm")) {
            play.slots[2].setDrawable(new TextureRegionDrawable(item.getTexture()));
            inventory[2] = item;
            numberOfItems[2]++;

        } else if (item.getName().equals("Holzmauer") || item.getName().equals("Steinmauer")) {
            play.slots[3].setDrawable(new TextureRegionDrawable(item.getTexture()));
            inventory[3] = item;
            numberOfItems[3]++;

        } else if (item.getName().equals("Fallgrube")) {
            play.slots[4].setDrawable(new TextureRegionDrawable(item.getTexture()));
            inventory[4] = item;
            numberOfItems[4]++;

        }

    }

    // Self explanatory
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

        if (keyListener.checkCollision().equals("TRAP")) {
            fallX = this.getX();
            fallY = this.getY();
            isFalling = true;
            play.auTrap.play();
        }

    }

    @Override
    public void draw(Batch spriteBatch) {

        // Zeitspanne zwischen aktuellem und vorherigem Frame
        delta = Gdx.graphics.getDeltaTime();
        update();
        super.draw(spriteBatch);
    }

    private void update() {

        // Apply speed
        velocity.y -= speed * delta;
        velocity.x -= speed * delta;

        // Clamp velocity (Beschleunigung einschränken)
        if(!isFalling) {
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
        }else {

            if (getScaleX() == 0) {
                fallCounter = 0;
                isFalling = false;
            }else {
                setScale(getScaleX() - delta, getScaleY() - delta);
                setPosition(fallX, fallY);
            }
        }

        keyListener.handleInput();
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

    public Item[] getInventory() {
        return inventory;
    }

    public void setInventory(Item[] inventory) {
        this.inventory = inventory;
    }

    public void removeInventoryItem(int index) {

        switch (index) {

        case 2:
            play.slots[2]
                    .setDrawable(new TextureRegionDrawable(new Texture("img/transparent.png")));
            break;

        case 3:
            play.slots[3]
                    .setDrawable(new TextureRegionDrawable(new Texture("img/transparent.png")));
            break;

        case 4:
            play.slots[4]
                    .setDrawable(new TextureRegionDrawable(new Texture("img/transparent.png")));
            break;

        }

    }

    public int[] getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int[] numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public void increaseNumberOfItems(int index, int num) {
        numberOfItems[index] += num;

        if (numberOfItems[index] <= 0) {
            removeInventoryItem(index);
        }
    }

}
