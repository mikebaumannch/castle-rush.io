package io.castlerush;

import io.castlerush.items.ItemLoader;
import io.castlerush.structures.StructureLoader;

public class GameManager {
    
    //Konstruktor
    public GameManager() {
        ItemLoader.loadItems();
        StructureLoader.loadStructures();
    }

}
