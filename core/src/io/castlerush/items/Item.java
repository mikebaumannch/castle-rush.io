package io.castlerush.items;

import com.badlogic.gdx.graphics.Texture;

public abstract class Item {
    
    String name;
    Texture icon;
    int price;
    String desc;
    
    public Item(String name, Texture icon, int price, String desc) {
        
        this.name = name;
        this.icon = icon;
        this.price = price;
        this.desc = desc;
        
    }
    
    void useItem() {
        
    }

}
