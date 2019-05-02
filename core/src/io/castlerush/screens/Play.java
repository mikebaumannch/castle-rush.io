package io.castlerush.screens;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import io.castlerush.Client;
import io.castlerush.Player;
import io.castlerush.Server;
import io.castlerush.gui.Shop;
import io.castlerush.items.ItemLoader;
import io.castlerush.structures.Structure;
import io.castlerush.structures.StructureCastle;
import io.castlerush.structures.StructureLoader;

public class Play implements Screen, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Game game;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private float screenWidth, screenHeight;
    private Stage stage;
    private Skin mySkin;
    private Label gameTitle, heartTitle, timeToCoinGenTitle;
    private Label[] lblNumberOfItems = new Label[5];
    private int selectedItem, mapWidth, mapHeight, tileWidth, tileHeight, timeToCoinGen;
    private float elapsedTime;
    private double distanceBetweenPlayerAndCastle, distanceBetweenPlayerAndOpponent;

    public Player player, opponent;
    public List<Player> oppenents = new ArrayList<Player>();
    public StructureCastle castle;
    public StructureCastle opponentCastle;
    public Sound[] auDamage = new Sound[4];
    public Sound auDeath, auKill, auLost, auTrap;
    public Batch batch;
    public String username;
    public Table tableInventory, tableUpgrade;
    public TextButton buttonShop, buttonExit, buttonBuy, buttonUpgrade;
    public Image[] slots = new Image[5];
    public Image selectField;
    public boolean shopIsOpen = false;
    public InputMultiplexer inputMulti = new InputMultiplexer();
    public TiledMap map;
    public List<Structure> structuresOnMap = new ArrayList<Structure>();
    public List<Structure> coins = new ArrayList<Structure>();

    public Play(String username) {

        this.username = username;

        // Initializing map, tiles etc.
        map = new TmxMapLoader().load("maps/maps.tmx");
        mapWidth = map.getProperties().get("width", Integer.class);
        mapHeight = map.getProperties().get("width", Integer.class);
        tileWidth = map.getProperties().get("tilewidth", Integer.class);
        tileHeight = map.getProperties().get("tileheight", Integer.class);

        // Initializing renderers
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        renderer = new OrthogonalTiledMapRenderer(map);
        batch = renderer.getBatch();

        // Setting up camera
        camera = new OrthographicCamera();
        camera.zoom = 1 / 3f;

        // Initializing GUI
        stage = new Stage();

        // Loading ressources such as items, structures etc.
        player = new Player(username, (new Sprite(new Texture("img/player.png"))), 0, 100, true,
                map, this);
        player.setSize(tileWidth * 2, tileHeight * 2);
        castle = new StructureLoader().castleLvl1;

        // TEST
        opponent = new Player("Gegner", (new Sprite(new Texture("img/player.png"))), 0, 100, true,
                map, this);
        opponent.setSize(tileWidth * 2, tileHeight * 2);
        opponentCastle = new StructureLoader().castleLvl1;
        opponentCastle.setSize(tileWidth * 8, tileHeight * 8);

        for (Sound au : auDamage) {
            au = Gdx.audio.newSound(Gdx.files.internal("audio/damage0.ogg"));
        }
        auDamage[0] = Gdx.audio.newSound(Gdx.files.internal("audio/damage0.ogg"));

        auDamage[0] = Gdx.audio.newSound(Gdx.files.internal("audio/damage0.ogg"));

        // Set random spawn point for player
        int randomMapX = ThreadLocalRandom.current().nextInt(16, (mapWidth - 3) * 16);
        int randomMapY = ThreadLocalRandom.current().nextInt(16, (mapHeight - 3) * 16);

        // Spawn the player and it's castle
        player.setX(randomMapX);
        player.setY(randomMapY);
        castle.setBounds(player.getX(), player.getY(), tileWidth * 8, tileHeight * 8);

        auTrap = Gdx.audio.newSound(Gdx.files.internal("audio/trap.ogg"));
        auKill = Gdx.audio.newSound(Gdx.files.internal("audio/kill.ogg"));
        auDeath = Gdx.audio.newSound(Gdx.files.internal("audio/deth.ogg"));
        auLost = Gdx.audio.newSound(Gdx.files.internal("audio/lost.ogg"));

        // Setting up input processors
        inputMulti.addProcessor(stage);
        inputMulti.addProcessor(player.keyListener);
        Gdx.input.setInputProcessor(inputMulti);
    }

    public void respawnPlayer() {
        if (!player.isCastleAlive) {
            player = null;
        } else {
            player.setPosition(castle.getX(), castle.getY());
        }
    }

    public void createPlayer() {
        Player opponent = new Player(username, (new Sprite(new Texture("img/player.png"))), 0, 100,
                true, map, this);
        opponent.setSize(tileWidth * 2, tileHeight * 2);
        // StructureCastle castleOpponent = new StructureLoader().castleLvl1;
        oppenents.add(opponent);
        // structuresOnMap.add(castleOpponent);
    }

    @Override
    public void show() {

        // Generates the coins on the map
        generateCoins();

        // Show HUD & GUI elements
        createHud();
        createItembar(stage);
        Shop.createShop(mySkin, stage, player, this);
        createTableUpgrade();
        createButtonListener();
    }

    private void createTableUpgrade() {

        // Upgrade Button
        tableUpgrade = new Table();
        buttonUpgrade = new TextButton("Upgrade", mySkin, "small");

        // Create components
        // Title
        Label upgradeLabel = new Label("" + new StructureLoader().castleLvl2.getName() + " Level 2",
                mySkin, "default");
        Label upgradePriceLabel = new Label("Coins: " + new ItemLoader().castleLvl2.getPrice(),
                mySkin, "default");

        // Add Components
        buttonUpgrade.setTransform(true);
        buttonUpgrade.setScale(0.9F);

        tableUpgrade.add(upgradeLabel).width(100);
        tableUpgrade.row();
        tableUpgrade.add(upgradePriceLabel).width(100);
        tableUpgrade.row();
        tableUpgrade.add(buttonUpgrade).width(100);

        tableUpgrade.setPosition(100, 100);
        tableUpgrade.setVisible(false);

        stage.addActor(tableUpgrade);

    }

    private void createItembar(Stage stage) {

        // Create Table
        tableInventory = new Table(mySkin);
        tableInventory.setWidth(303);
        tableInventory.setHeight(60);
        tableInventory.setBackground(new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal("img/itembar.png")))));
        tableInventory.setPosition(Gdx.graphics.getWidth() / 2 - tableInventory.getWidth() / 2, 0);

        // transparent image
        for (int i = 0; i < slots.length; i++) {

            // Initialize the slots
            slots[i] = new Image(new Texture(Gdx.files.internal("img/transparent.png")));

            // Add components to table
            tableInventory.add(slots[i]).width(48).expandX();
        }

        // Give player start item
        slots[0].setDrawable(new TextureRegionDrawable(new ItemLoader().fist.getTexture()));
        player.getInventory()[0] = new ItemLoader().fist;

        // Selectfield
        Texture selectFieldImg = new Texture(Gdx.files.internal("img/selectedItembar.png"));
        selectField = new Image(selectFieldImg);
        selectField.setSize(60, 60);
        selectField.setPosition(tableInventory.getX(), 0);
        selectedItem = 0;

        // Itemanzahl
        int count = 0;
        for (Label lbl : lblNumberOfItems) {
            lbl = new Label("" + player.getNumberOfItems()[count], mySkin);
            stage.addActor(lbl);
            count++;
        }

        // Add table to dialog
        stage.addActor(tableInventory);
        stage.addActor(selectField);
    }

    private void createHud() {

        // Texture
        mySkin = new Skin(Gdx.files.internal("skins/glassy-ui.json"));

        // Label title
        gameTitle = new Label("Name: " + player.getName() + "\nCoins: " + player.getCoins(),
                mySkin);
        gameTitle.setSize(100, 100);
        gameTitle.setPosition(20, screenHeight - gameTitle.getHeight() / 2 - 30);
        gameTitle.setAlignment(Align.left);

        timeToCoinGenTitle = new Label("Time To New Coins: " + timeToCoinGen, mySkin);
        timeToCoinGenTitle.setSize(100, 100);
        timeToCoinGenTitle.setPosition(20, screenHeight - gameTitle.getHeight() / 2 - 60);
        timeToCoinGenTitle.setAlignment(Align.left);

        // Button Spiel beenden
        buttonExit = new TextButton("Spiel beenden (ESC)", mySkin, "small");
        buttonExit.setName("buttonExit");
        buttonExit.setPosition(screenWidth - 20 - buttonExit.getWidth(),
                screenHeight - gameTitle.getHeight() / 2 - 20);

        // Button Shop öffnen
        buttonShop = new TextButton("Shop oeffnen (E)", mySkin, "small");
        buttonShop.setName("buttonShop");
        buttonShop.setPosition(screenWidth - 45 - buttonShop.getWidth(),
                screenHeight - buttonExit.getHeight() - 80);
        buttonShop.setWidth(buttonExit.getWidth());

        // Herz für die Lebenspunkte hinzufügen
        Texture texture = new Texture(Gdx.files.internal("img/heart.png"));
        Image heart = new Image(texture);
        heart.setSize(texture.getWidth() / 6, texture.getHeight() / 6);
        heart.setPosition(screenWidth - heart.getWidth() / 2 - 20, 20);

        // Anzeige der Lebenspunkte hinzufügen
        heartTitle = new Label("" + player.getHealth(), mySkin);
        heartTitle.setSize(100, 100);
        heartTitle.setPosition(screenWidth - heart.getWidth() / 2 - heart.getWidth() - 20, 0);
        heartTitle.setAlignment(Align.left);

        // Add Actor
        stage.addActor(gameTitle);
        stage.addActor(timeToCoinGenTitle);
        stage.addActor(heart);
        stage.addActor(heartTitle);
        stage.addActor(buttonShop);
        stage.addActor(buttonExit);

    }

    // Updates the HUD
    private void updateHUD() {
        gameTitle.setText("Name: " + player.getName() + "\nCoins: " + player.getCoins());
        heartTitle.setText(player.getHealth());
        timeToCoinGenTitle.setText("Time To New Coins: " + timeToCoinGen);

        if (distanceBetweenPlayerAndCastle < 200) {
            // Show upgrade
            tableUpgrade.setVisible(true);
        } else {
            tableUpgrade.setVisible(false);
        }
    }

    // Erstellt ein Listener für alle Buttons
    private void createButtonListener() {

        // Listener
        buttonExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.exit(0);
            }
        });

        buttonShop.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Shop.showShop();
            }
        });

        buttonUpgrade.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                int price = new ItemLoader().castleLvl2.getPrice();

                if (player.getCoins() >= price) {

                    float posX = castle.getX();
                    float posY = castle.getY();
                    float width = castle.getWidth();
                    float height = castle.getHeight();

                    castle = player.upgrade(castle);
                    castle.setBounds(posX, posY, width, height);

                    player.setCoins(player.getCoins() - price);

                }
            }
        });
    }

    // Randomly generates coin
    private void generateCoins() {

        // Clears all coins from map
        coins.clear();

        // Initializing new coins
        for (int i = 0; i < 100; i++) {
            coins.add(new StructureLoader().coin);
        }

        // Sets random spawn point for each coin
        for (Structure coin : coins) {

            coin.setSize(8, 8);

            int randomMapX = ThreadLocalRandom.current().nextInt(16, (mapWidth - 3) * 16);
            int randomMapY = ThreadLocalRandom.current().nextInt(16, (mapHeight - 3) * 16);

            coin.setPosition(randomMapX, randomMapY);
        }

        player.earnCoins();

    }

    // Builds the placed structures
    private void drawStructures(List<Structure> structuresOnMap) {

        if (castle != null) {
            castle.draw(batch);
        }
        opponentCastle.draw(batch);

        for (Structure coin : coins) {
            coin.draw(batch);
        }

        for (Structure structure : structuresOnMap) {

            structure.draw(batch);
        }
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(100 / 255f, 155 / 255f, 255 / 255f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Checks elapsed time (delta -> seconds passed since last render)
        elapsedTime += delta;
        timeToCoinGen = (int) (60 - elapsedTime + 1);

        // Reset timer and spawn new coins
        if (elapsedTime > 60.0) {
            elapsedTime = 0;
            generateCoins();
        }

        updateHUD();

        if (castle != null) {
            distanceBetweenPlayerAndCastle = Math.sqrt(Math.pow((player.getX() - castle.getX()), 2)
                    + Math.pow((player.getY() - castle.getY()), 2));
        }

        distanceBetweenPlayerAndOpponent = Math.sqrt(Math.pow((player.getX() - opponent.getX()), 2)
                + Math.pow((player.getY() - opponent.getY()), 2));

        camera.position.set(player.getX() + player.getWidth() / 2,
                player.getY() + player.getHeight() / 2, 0);

        camera.update();

        renderer.setView(camera);
        renderer.render();

        batch.begin();

        drawStructures(structuresOnMap);

        player.draw(batch);
        opponent.draw(batch);
        /*
         * for (Player o : oppenents) { o.draw(batch); }
         */

        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public double getDistanceBetweenPlayerAndOpponent() {
        return distanceBetweenPlayerAndOpponent;
    }

    public void setDistanceBetweenPlayerAndOpponent(double distanceBetweenPlayerAndOpponent) {
        this.distanceBetweenPlayerAndOpponent = distanceBetweenPlayerAndOpponent;
    }
}
