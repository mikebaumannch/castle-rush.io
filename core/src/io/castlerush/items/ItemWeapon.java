package io.castlerush.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ItemWeapon extends Item {
    
    int damage;

    public ItemWeapon(String name, int damage, Sprite icon, int price, String desc) {
        super(name, icon, price, desc);
        
        this.damage = damage;
    }

}
