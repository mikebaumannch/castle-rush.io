package io.castlerush.structures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class StructureLoader {
    
    // Coin
    public Structure coin = new Structure("Coin", new Sprite(new Texture("img/coin.png")));
    
    // Castles
    public StructureCastle castleLvl1 = new StructureCastle("Rathaus", new Sprite(new Texture("buildings/castle-lvl1.png")), 1000, 1);
    public StructureCastle castleLvl2 = new StructureCastle("Ratsgebäude", new Sprite(new Texture("buildings/castle-lvl2.png")), 2000, 2);
    
    // Towers
    public StructureTower towerLvl1 = new StructureTower ("Bogenschützenturm", new Sprite(new Texture("buildings/towerlvl1.png")), 100);
    //public StructureTower towerLvl2 = new StructureTower ("Kanonenturm", new Sprite(new Texture("buildings/archery-tower-lvl2.png")), 200);
    
    // Walls
    public StructureWall woodWall = new StructureWall("Holzmauer", new Sprite(new Texture("badlogic.jpg")), 100);
    //public StructureWall stoneWall = new StructureWall("Steinmauer", new Sprite(new Texture("badlogic.jpg")), 200);
    
    // Traps
    public StructureTrap trap = new StructureTrap("Fallgrube", new Sprite(new Texture("buildings/trap.png")), 100);

}
