package io.castlerush.structures;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class StructureTower extends Structure {
    
    int damage;

    public StructureTower(String name, Sprite tex, int damage) {
        super(name, tex);
        
        this.damage = damage;
    }

}
