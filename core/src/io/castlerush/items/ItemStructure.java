package io.castlerush.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.castlerush.Player;
import io.castlerush.structures.Structure;
import io.castlerush.structures.StructureLoader;

public class ItemStructure extends Item {

    public ItemStructure(String name, Sprite icon, int price, String desc) {
        super(name, icon, price, desc);
    }

}
