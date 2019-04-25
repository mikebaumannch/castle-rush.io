package io.castlerush.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ItemLoader {
    
    // Weapons
    public static ItemWeapon woodSword;
    public static ItemWeapon stoneSword;
    public static ItemWeapon ironSword;
    public static ItemWeapon slingShot;
    public static ItemWeapon bow;
    
    // Structures
    public static ItemStructure castleLvl1;
    public static ItemStructure castleLvl2;
    public static ItemStructure towerLvl1;
    public static ItemStructure towerLvl2;
    public static ItemStructure woodWall;
    public static ItemStructure stoneWall;
    public static ItemStructure trap;
    
 // Defines every item in the game
    public static void loadItems() {
        
        //Weapons
        woodSword = new ItemWeapon("Holzschwert", 10, new Sprite(new Texture("buildings/castle-lvl1.jpg")), 100, "Level 1");
        stoneSword = new ItemWeapon("Steinschwert", 15, null, 500, "Level 2");
        ironSword = new ItemWeapon("Eisenschwert", 20, null, 1000, "Level 3");
        slingShot = new ItemWeapon("Steinschleuder", 5, new Sprite( new Texture("weapons/slingshot.svg")), 100, "Level 1");
        bow = new ItemWeapon("Bogen", 15, new Sprite( new Texture("weapons/bow.svg")), 500, "Level 2");
        
        //Structures
        castleLvl1 = new ItemStructure("Rathaus", null, 0, "Level 1");
        castleLvl2 = new ItemStructure("Ratsgebäude", null, 500, "Level 2");   
        towerLvl1 = new ItemStructure("Bogenschützenturm", null, 500, "Level 1");
        towerLvl2 = new ItemStructure("Kanonenturm", null, 1000, "Level 2");
        woodWall = new ItemStructure("Holzmauer", null, 100, "Level 1");
        stoneWall = new ItemStructure("Steinmauer", null, 200, "Level 2");
        trap = new ItemStructure("Fallgrube", null, 300, "Level 1");
        
    }

}
