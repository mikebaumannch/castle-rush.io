package io.castlerush;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite {

    public String name;
    public int coins;
    public int health;
    
    public boolean isCastleAlive = true;
    public TiledMapTileLayer layer;
    private Vector2 velocity = new Vector2();

    public Player(String name, Sprite skin, int coins, int health, boolean isCastleAlive,
            TiledMapTileLayer layer) {
        super(skin);
        this.name = name;
        this.coins = coins;
        this.health = health;
        this.isCastleAlive = isCastleAlive;
        this.layer = layer;
    }

    @Override
    public void draw(Batch spriteBatch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }

    private void update(float delta) {
        /*
        if (!checkCollision) {
            setX(getX() + velocity.x * delta);
        }

        if (!checkCollision) {
            setY(getY() + velocity.y * delta);
        }*/

    }

    void walk() {
    }

    void attack() {
    }

    void buy() {
    }

    public int getHealth() {
        return health;
    }
}
