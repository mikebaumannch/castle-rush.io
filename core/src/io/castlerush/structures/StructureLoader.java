package io.castlerush.structures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.castlerush.items.ItemStructure;

public class StructureLoader {
    
    // Coin
    public Structure coin = new Structure("Coin", new Sprite(new Texture("img/coin.png")));
    
    // Castles
    public StructureCastle castleLvl1 = new StructureCastle("Rathaus", new Sprite(new Texture("buildings/castle-lvl2.png")), 1000);
    public StructureCastle castleLvl2 = new StructureCastle("Ratsgebäude", new Sprite(new Texture("buildings/castle-lvl2.png")), 2000);
    
    // Towers
    //public StructureTower towerLvl1 = new StructureTower ("Bogenschützenturm", new Sprite(new Texture("buildings/archery-tower-lvl1.png")), 100);
    //public StructureTower towerLvl2 = new StructureTower ("Kanonenturm", new Sprite(new Texture("buildings/archery-tower-lvl2.png")), 200);
    
    // Walls
    //public static StructureWall woodWall = new StructureWall("Holzmauer", new Sprite(new Texture("badlogic.jpg")), 100);
    //public static StructureWall stoneWall = new StructureWall("Steinmauer", new Sprite(new Texture("badlogic.jpg")), 200);
    
    // Traps
    public StructureTrap trap;

}
