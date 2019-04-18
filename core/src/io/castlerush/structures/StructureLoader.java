package io.castlerush.structures;

import com.badlogic.gdx.graphics.Texture;

public class StructureLoader {
    
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
        
        // Castles
        castleLvl1 = new StructureCastle("Rathaus", new Texture("badlogic.jpg"), 1000);
        castleLvl2 = new StructureCastle("Ratsgebäude", new Texture("badlogic.jpg"), 2000);
        
        // Towers
        towerLvl1  = new StructureTower ("Bogenschützenturm", new Texture("badlogic.jpg"), 100);
        towerLvl2  = new StructureTower ("Kanonenturm", new Texture("badlogic.jpg"), 200);
        
        // Walls
        woodWall = new StructureWall("Holzmauer", new Texture("badlogic.jpg"), 100);
        stoneWall = new StructureWall("Steinmauer", new Texture("badlogic.jpg"), 200);
        
    }

}
