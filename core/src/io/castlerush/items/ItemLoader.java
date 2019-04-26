package io.castlerush.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ItemLoader {
    
    // Weapons
    public ItemWeapon woodSword = new ItemWeapon("Holzschwert", 10, new Sprite(new Texture("weapons/woodSword.png")), 100, "Level 1");
    public ItemWeapon stoneSword = new ItemWeapon("Steinschwert", 15, new Sprite(new Texture("weapons/stoneSword.png")), 500, "Level 2");
    public ItemWeapon ironSword = new ItemWeapon("Eisenschwert", 20, new Sprite(new Texture("weapons/ironSword.png")), 1000, "Level 3");
    public ItemWeapon slingShot = new ItemWeapon("Steinschleuder", 5, new Sprite( new Texture("weapons/slingshot.png")), 100, "Level 1");
    public ItemWeapon bow = new ItemWeapon("Bogen", 15, new Sprite( new Texture("weapons/bow.png")), 500, "Level 2");
    
    // Structures
    public ItemStructure castleLvl1 = new ItemStructure("Rathaus", new Sprite(new Texture("buildings/castle-lvl1.jpg")), 0, "Level 1");
    public ItemStructure castleLvl2 = new ItemStructure("Ratsgebäude", new Sprite(new Texture("buildings/castle-lvl2.png")), 500, "Level 2");
    public ItemStructure towerLvl1 = new ItemStructure("Bogenschützenturm", new Sprite(new Texture("buildings/towerlvl1.png")), 500, "Level 1");
    public ItemStructure towerLvl2 = new ItemStructure("Kanonenturm", new Sprite(new Texture("buildings/towerlvl2.png")), 1000, "Level 2");
   // public ItemStructure woodWall = new ItemStructure("Holzmauer", new Sprite(new Texture("buildings/woodWall.png")), 100, "Level 1");
    public ItemStructure stoneWall = new ItemStructure("Steinmauer", new Sprite(new Texture("buildings/stoneWall.png")), 200, "Level 2");
    public ItemStructure trap = new ItemStructure("Fallgrube", new Sprite(new Texture("buildings/trap.png")), 300, "Level 1");

}
