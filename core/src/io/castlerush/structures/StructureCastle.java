package io.castlerush.structures;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class StructureCastle extends Structure {
    
    private int health;
    private int level;

    public StructureCastle(String name, Sprite tex, int health, int level) {
        super(name, tex);
        
        this.setHealth(health);
        this.setLevel(level);
    }
    
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
