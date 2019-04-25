package io.castlerush.structures;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class StructureWall extends Structure {
    
    int health;

    public StructureWall(String name, Sprite tex, int health) {
        super(name, tex);
        
        this.health = health;
    }

}
