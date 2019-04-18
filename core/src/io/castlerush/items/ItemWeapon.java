package io.castlerush.items;

import com.badlogic.gdx.graphics.Texture;

public class ItemWeapon extends Item {
    
    int damage;

    public ItemWeapon(String name, int damage, Texture icon, int price, String desc) {
        super(name, icon, price, desc);
        
        this.damage = damage;
    }

}
