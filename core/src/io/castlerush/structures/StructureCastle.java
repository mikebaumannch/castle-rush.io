package io.castlerush.structures;

import com.badlogic.gdx.graphics.Texture;

public class StructureCastle extends Structure {
    
    int health;

    public StructureCastle(String name, Texture tex, int health) {
        super(name, tex);
        
        this.health = health;
    }

}
