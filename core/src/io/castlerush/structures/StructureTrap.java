package io.castlerush.structures;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class StructureTrap extends Structure {
    
    private int damage;

    public StructureTrap(String name, Sprite tex, int damage) {
        super(name, tex);
        
        this.damage = damage;
    }

}
