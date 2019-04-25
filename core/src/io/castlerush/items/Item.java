package io.castlerush.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Item extends Sprite {
    
    String name;
   
    Sprite icon;
    int price;
    String desc;
    
    public Item(String name, Sprite icon, int price, String desc) {
        super(icon);
        this.name = name;
        this.icon = icon;
        this.price = price;
        this.desc = desc;
        
    }
    
    void useItem() {
        
    }
    
    public String getName() {
        return name;
    }
}
