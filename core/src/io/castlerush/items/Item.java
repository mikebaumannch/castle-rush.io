package io.castlerush.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Item extends Sprite {
    
    private String name;
    private Sprite icon;
    private String desc;
    private int price;
    
    public Item(String name, Sprite icon, int price, String desc) {
        super(icon);
        this.name = name;
        this.setIcon(icon);
        this.setPrice(price);
        this.setDesc(desc);
        
    }
    
    void useItem() {
        
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Sprite getIcon() {
        return icon;
    }

    public void setIcon(Sprite icon) {
        this.icon = icon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
