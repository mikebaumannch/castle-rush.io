package io.castlerush.structures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.castlerush.items.ItemStructure;

public class StructureLoader {
    
    // Coin
    public static Structure coin;
    
    // Castles
    public static StructureCastle castleLvl1;
    public static StructureCastle castleLvl2;
    
    // Towers
    public static StructureTower towerLvl1;
    public static StructureTower towerLvl2;
    
    // Walls
    public static StructureWall woodWall;
    public static StructureWall stoneWall;
    
    // Traps
    public static StructureTrap trap;
    
    // Defines every structure in the game
    public static void loadStructures() {
        
        // Coin
        coin = new Structure("Coin", new Sprite(new Texture("img/coin.png")));
        
        // Castles
        castleLvl1 = new StructureCastle("Rathaus", new Sprite(new Texture("buildings/castle-lvl2.png")), 1000);
        /*
        castleLvl2 = new StructureCastle("Ratsgebäude", new Sprite(new Texture("buildings/castle-lvl2.png")), 2000);
        
        // Towers
        towerLvl1  = new StructureTower ("Bogenschützenturm", new Sprite(new Texture("buildings/archery-tower-lvl1.png")), 100);
        towerLvl2  = new StructureTower ("Kanonenturm", new Sprite(new Texture("buildings/archery-tower-lvl2.png")), 200);
        
        // Walls
        woodWall = new StructureWall("Holzmauer", new Sprite(new Texture("badlogic.jpg")), 100);
        stoneWall = new StructureWall("Steinmauer", new Sprite(new Texture("badlogic.jpg")), 200);
        */
        
    }

}
