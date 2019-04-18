package io.castlerush.structures;

import com.badlogic.gdx.graphics.Texture;

public class StructureTower extends Structure {
    
    int damage;

    public StructureTower(String name, Texture tex, int damage) {
        super(name, tex);
        
        this.damage = damage;
    }

}
