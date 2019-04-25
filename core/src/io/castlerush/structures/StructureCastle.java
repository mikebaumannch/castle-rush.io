package io.castlerush.structures;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class StructureCastle extends Structure {
    
    int health;

    public StructureCastle(String name, Sprite tex, int health) {
        super(name, tex);
        
        this.health = health;
    }

}
