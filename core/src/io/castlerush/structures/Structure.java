package io.castlerush.structures;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Structure extends Sprite {
    
    private String name;
    private Sprite tex;
    
    public Structure(String name, Sprite tex) {
        super(tex);
        this.setName(name);
        this.tex = tex;
    }
    
    @Override
    public void draw(Batch spriteBatch) {
        super.draw(spriteBatch);
    }
    
    public void build(int tile[][]) {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
