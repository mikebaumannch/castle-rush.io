package io.castlerush;

public class Player {
    
    public String name;
    public int coins;
    public int health;
    public boolean isCastleAlive = true;
    public float position;
    
    private float speed = 1.0F;
    
    public Player(String name, int coins, int health, boolean isCastleAlive, float position) {
        this.name=name;
        this.coins=coins;
        this.health=health;
        this.isCastleAlive=isCastleAlive;
        this.position=position;
    }
    
    void walk() {}
    void attack() {}
    void buy( ) {}

}
