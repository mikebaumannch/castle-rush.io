package io.castlerush.structures;

import com.badlogic.gdx.graphics.Texture;

public class StructureTrap extends Structure {
    
    int damage;

    public StructureTrap(String name, Texture tex, int damage) {
        super(name, tex);
        
        this.damage = damage;
    }

}
