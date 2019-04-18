package io.castlerush.structures;

import com.badlogic.gdx.graphics.Texture;

public class StructureWall extends Structure {
    
    int health;

    public StructureWall(String name, Texture tex, int health) {
        super(name, tex);
        
        this.health = health;
    }

}
