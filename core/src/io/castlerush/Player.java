package io.castlerush;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import io.castlerush.items.Item;
import io.castlerush.screens.Play;

public class Player extends Sprite {
    
    private Play play;
    public String name;
    public int coins;
    public int health;
    public boolean isCastleAlive = true;
    public TiledMap map;
    public KeyListener keyListener;
    private Vector2 velocity = new Vector2();
    private float speed = 100, delta;
    private List<Item> inventory = new ArrayList<Item>();
    
    public Player(String name, Sprite skin, int coins, int health, boolean isCastleAlive, TiledMap map, Play play) {

        super(skin);
        this.play = play;
        this.name = name;
        this.coins = coins;
        this.health = health;
        this.isCastleAlive = isCastleAlive;
        this.map = map; 
        this.keyListener = new KeyListener(this, map, play);
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

        // Clamp velocity
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
    }

    void walk(int key) {

        switch (key) {

        case 0:
            if (!keyListener.checkCollision().equals("LEFT")) {
                setX(getX() + velocity.x * delta);
            }
            break;

        case 1:
            if (!keyListener.checkCollision().equals("RIGHT")) {
                setX(getX() + -velocity.x * delta);
            }
            break;

        case 2:
            if (!keyListener.checkCollision().equals("TOP")) {
                setY(getY() + -velocity.y * delta);
            }
            break;

        case 3:
            if (!keyListener.checkCollision().equals("BOTTOM")) {
                setY(getY() + velocity.y * delta);
            }
            break;
        }
    }

    void attack() {
    }

    public void buy(Item item) {
        inventory.add(item);
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

    public int getCoins() {
        return coins;
    }
}
