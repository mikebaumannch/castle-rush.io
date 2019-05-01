package io.castlerush.items;

import java.io.Serializable;

import com.badlogic.gdx.graphics.g2d.Sprite;

import io.castlerush.Player;

public abstract class Item extends Sprite implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
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
    
    public void useItem(Player player) {
        
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
