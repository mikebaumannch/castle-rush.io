package io.castlerush;

import io.castlerush.items.ItemLoader;
import io.castlerush.structures.StructureLoader;

public class GameManager {
    
    public GameManager() {
        ItemLoader.loadItems();
        StructureLoader.loadStructures();
    }

}
