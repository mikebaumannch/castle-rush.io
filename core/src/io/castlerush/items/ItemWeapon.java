package io.castlerush.items;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class ItemWeapon extends Item{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int damage;

    public ItemWeapon(String name, int damage, Sprite icon, int price, String desc) {
        super(name, icon, price, desc);
        
        this.setDamage(damage);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

}
