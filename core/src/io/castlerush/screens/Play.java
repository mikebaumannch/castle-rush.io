package io.castlerush.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import io.castlerush.KeyListener;
import io.castlerush.Player;
import io.castlerush.gui.Shop;
import io.castlerush.items.Item;
import io.castlerush.items.ItemLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import io.castlerush.KeyListener;
import io.castlerush.Player;
import io.castlerush.items.ItemLoader;
import io.castlerush.structures.Structure;
import io.castlerush.structures.StructureLoader;

public class Play implements Screen {

    // PLAYER
    private Player player;
    private Structure castle;

    // RENDERER
    public Batch batch;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    // HUD & GUI
    private String username;
    private Stage stage;
    private Skin mySkin;
    private Dialog upgradeDialog;

    // GUI
    public Table tableInventory, upgradeTable;
    private Label gameTitle, heartTitle, timeToCoinGenTitle, upgradeTitle;
    private Dialog dialog, inventory;
    public TextButton buttonShop, buttonExit;
    private TextButtonStyle textButtonStyle;

    public TextButton buttonBuy;
    public Image weaponImageSlot0, transparentImageSlot1, transparentImageSlot2,
            transparentImageSlot3, transparentImageSlot4, selectField;
    private int selectedItem;
    public boolean shopIsOpen = false;
    public InputMultiplexer inputMulti = new InputMultiplexer();

    // MAP
    private TiledMap map;
    private int mapWidth;
    private int mapHeight;
    private int tileWidth;
    private int tileHeight;

    // STRUCTURES
    public List<Structure> structuresOnMap = new ArrayList<Structure>();
    public List<Structure> coins = new ArrayList<Structure>();

    // UTILS
    private float elapsedTime;
    private int timeToCoinGen;
    private double distanceBetweenPlayerAndCastle;

    public Play(String username) {

        // Initializing map, tiles etc.
        map = new TmxMapLoader().load("maps/maps.tmx");
        mapWidth = map.getProperties().get("width", Integer.class);
        mapHeight = map.getProperties().get("width", Integer.class);
        tileWidth = map.getProperties().get("tilewidth", Integer.class);
        tileHeight = map.getProperties().get("tileheight", Integer.class);

        // Initializing renderers
        renderer = new OrthogonalTiledMapRenderer(map);
        batch = renderer.getBatch();

        // Setting up camera
        camera = new OrthographicCamera();
        camera.zoom = 1 / 3f;

        // Initializing GUI
        stage = new Stage();

        // Loading ressources such as items, structures etc.
        this.username = username;
        player = new Player(username, (new Sprite(new Texture("img/player.png"))), 0, 100, true,
                map, this);
        player.setSize(tileWidth * 2, tileHeight * 2);
        generateCoins();
        castle = new StructureLoader().castleLvl1;

        // Setting up input processors
        inputMulti.addProcessor(stage);
        inputMulti.addProcessor(player.keyListener);
        Gdx.input.setInputProcessor(inputMulti);
    }

    @Override
    public void show() {

        // Set random spawn point for player
        int randomMapX = ThreadLocalRandom.current().nextInt(16, (mapWidth - 3) * 16);
        int randomMapY = ThreadLocalRandom.current().nextInt(16, (mapHeight - 3) * 16);

        // Spawn the player and it's castle
        player.setX(randomMapX);
        player.setY(randomMapY);
        player.placeStructure(castle);

        // Show HUD & GUI elements
        createHud();
        createItembar(stage);
        Shop.createShop(mySkin, stage, player, this);
        createTableUpgrade();
        createButtonListener();
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
    }

    private void createTableUpgrade() {

        // Upgrade Button
        TextButton upgradeButton = new TextButton("Upgrade", mySkin, "small");
        upgradeTable = new Table();

        // Create components
        // Title
        Label upgradeLabel = new Label("" + new StructureLoader().castleLvl2.getName() + " Level 2",
                mySkin, "default");
        Label upgradePriceLabel = new Label("Coins: " + new ItemLoader().castleLvl2.getPrice(),
                mySkin, "default");

        // Add Components
        upgradeLabel.setFontScale(0.9F);
        upgradePriceLabel.setFontScale(0.9F);
        upgradeButton.setTransform(true);
        upgradeButton.setScale(0.9F);       
       
        upgradeTable.add(upgradeLabel).width(100);
        upgradeTable.row();
        upgradeTable.add(upgradePriceLabel).width(100);
        upgradeTable.row();
        upgradeTable.add(upgradeButton).width(100);
        
        upgradeTable.setPosition(100, 100);
        upgradeTable.setVisible(false);
        
        stage.addActor(upgradeTable);
        
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
        weaponImageSlot0 = new Image(new Texture(Gdx.files.internal("weapons/fist.png")));
        player.getInventory()[0] = new ItemLoader().fist;
        transparentImageSlot1 = new Image(new Texture(Gdx.files.internal("img/transparent.png")));
        transparentImageSlot2 = new Image(new Texture(Gdx.files.internal("img/transparent.png")));
        transparentImageSlot3 = new Image(new Texture(Gdx.files.internal("img/transparent.png")));
        transparentImageSlot4 = new Image(new Texture(Gdx.files.internal("img/transparent.png")));

        // Add components to table
        tableInventory.add(weaponImageSlot0).width(48).expandX();
        tableInventory.add(transparentImageSlot1).width(48).expandX();
        tableInventory.add(transparentImageSlot2).width(48).expandX();
        tableInventory.add(transparentImageSlot3).width(48).expandX();
        tableInventory.add(transparentImageSlot4).width(48).expandX();

        // Selectfield
        Texture selectFieldImg = new Texture(Gdx.files.internal("img/selectedItembar.png"));
        selectField = new Image(selectFieldImg);
        selectField.setSize(60, 60);
        selectField.setPosition(tableInventory.getX(), 0);
        selectedItem = 0;

        // Add table to dialog
        stage.addActor(tableInventory);
        stage.addActor(selectField);
    }

    private void createHud() {

        mySkin = new Skin(Gdx.files.internal("skins/glassy-ui.json"));

        // Label title
        gameTitle = new Label("Name: " + player.getName() + "\nCoins: " + player.getCoins(),
                mySkin);
        gameTitle.setSize(100, 100);
        gameTitle.setPosition(20, Gdx.graphics.getHeight() - gameTitle.getHeight() / 2 - 30);
        gameTitle.setAlignment(Align.left);

        timeToCoinGenTitle = new Label("Time To New Coins: " + timeToCoinGen, mySkin);
        timeToCoinGenTitle.setSize(100, 100);
        timeToCoinGenTitle.setPosition(20,
                Gdx.graphics.getHeight() - gameTitle.getHeight() / 2 - 60);
        timeToCoinGenTitle.setAlignment(Align.left);

        // Button Spiel beenden
        buttonExit = new TextButton("Spiel beenden (ESC)", mySkin, "small");
        buttonExit.setName("buttonExit");
        buttonExit.setPosition(Gdx.graphics.getWidth() - 20 - buttonExit.getWidth(),
                Gdx.graphics.getHeight() - gameTitle.getHeight() / 2 - 20);

        // Button Shop öffnen
        buttonShop = new TextButton("Shop oeffnen (E)", mySkin, "small");
        buttonShop.setName("buttonShop");
        buttonShop.setPosition(Gdx.graphics.getWidth() - 45 - buttonShop.getWidth(),
                Gdx.graphics.getHeight() - buttonExit.getHeight() - 80);
        buttonShop.setWidth(buttonExit.getWidth());

        /*
         * Itembar anzeigen Texture itembar = new
         * Texture(Gdx.files.internal("img/itembar.png")); Image item = new
         * Image(itembar); item.setSize(itembar.getWidth(), itembar.getHeight());
         * item.setPosition(Gdx.graphics.getWidth() / 2 - item.getWidth() / 2, 0);
         */

        // Herz für die Lebenspunkte hinzufügen
        Texture texture = new Texture(Gdx.files.internal("img/heart.png"));
        Image heart = new Image(texture);
        heart.setSize(texture.getWidth() / 6, texture.getHeight() / 6);
        heart.setPosition(Gdx.graphics.getWidth() - heart.getWidth() / 2 - 20, 20);

        // Anzeige der Lebenspunkte hinzufügen
        heartTitle = new Label("" + player.getHealth(), mySkin);
        heartTitle.setSize(100, 100);
        heartTitle.setPosition(
                Gdx.graphics.getWidth() - heart.getWidth() / 2 - heart.getWidth() - 20, 0);
        heartTitle.setAlignment(Align.left);

        // Dialog
        dialog = new Dialog("", mySkin, "default");

        // Create Components of table
        // Title
        Label title = new Label("Shop", mySkin, "big");
        // INformations
        Label nameInformation = new Label("Name:", mySkin);
        Label priceInformation = new Label("Preis:", mySkin);
        // Wall
        Label wallLabel = new Label("Holzmauer", mySkin);
        Label wallPrice = new Label("50", mySkin);
        TextButton wallButton = new TextButton("Kaufen", mySkin, "small");
        // Archery
        Label archeryLabel = new Label("Bogenschützenturm", mySkin);
        Label archeryPrice = new Label("200", mySkin);
        TextButton archeryButton = new TextButton("Kaufen", mySkin, "small");
        // Trap
        Label trapLabel = new Label("Falle", mySkin);
        Label trapPrice = new Label("200", mySkin);
        TextButton trapButton = new TextButton("Kaufen", mySkin, "small");
        // Sword
        Label swordLabel = new Label("Holzschwert", mySkin);
        Label swordPrice = new Label("200", mySkin);
        TextButton swordButton = new TextButton("Kaufen", mySkin, "small");
        // Slingshot
        Label slingshotLabel = new Label("Slingshot", mySkin);
        Label slingshotdPrice = new Label("200", mySkin);
        TextButton slingshotButton = new TextButton("Kaufen", mySkin, "small");

        // Create Table
        Table table = new Table();

        // Add Components to table
        table.add(nameInformation).width(200);
        table.add(priceInformation).width(50);
        table.row();
        table.add(wallLabel).width(200);
        table.add(wallPrice).width(50);
        table.add(wallButton).width(100);
        table.row();
        table.add(archeryLabel).width(200);
        table.add(archeryPrice).width(50);
        table.add(archeryButton).width(100);
        table.row();
        table.add(trapLabel).width(200);
        table.add(trapPrice).width(50);
        table.add(trapButton).width(100);
        table.row();
        table.add(swordLabel).width(200);
        table.add(swordPrice).width(50);
        table.add(swordButton).width(100);
        table.row();
        table.add(slingshotLabel).width(200);
        table.add(slingshotdPrice).width(50);
        table.add(slingshotButton).width(100);

        dialog.hide();
        dialog.setWidth(400);
        dialog.setHeight(400);
        dialog.setPosition(Gdx.graphics.getWidth() / 2 - dialog.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - dialog.getHeight() / 2);

        // Settings
        title.setPosition(dialog.getWidth() / 2 - title.getWidth() / 2, dialog.getHeight());

        // Add table to dialog
        dialog.addActor(title);
        dialog.addActor(table);

        table.setPosition(dialog.getWidth() / 2 - table.getWidth() / 2, 210);

        // Add Actor
        stage.addActor(gameTitle);
        stage.addActor(timeToCoinGenTitle);
        stage.addActor(heart);
        stage.addActor(heartTitle);
        stage.addActor(buttonShop);
        stage.addActor(buttonExit);

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
            generateCoins();
            elapsedTime = 0;
        }
        
        updateHUD();

        distanceBetweenPlayerAndCastle = Math.sqrt(Math.pow((castle.getX() - player.getX()), 2)
                + Math.pow((castle.getY() - player.getY()), 2));

        camera.position.set(player.getX() + player.getWidth() / 2,
                player.getY() + player.getHeight() / 2, 0);
        camera.update();

        renderer.setView(camera);
        renderer.render();

        batch.begin();

        drawStructures(structuresOnMap);
        player.draw(batch);

        batch.end();

        stage.act();
        stage.draw();
    }

    // Updates the HUD
    private void updateHUD() {
        gameTitle.setText("Name: " + player.getName() + "\nCoins: " + player.getCoins());
        heartTitle.setText(player.getHealth());
        timeToCoinGenTitle.setText("Time To New Coins: " + timeToCoinGen);

        if (distanceBetweenPlayerAndCastle < 50) {
            // Show upgrade
            upgradeTable.setVisible(true);
        }else {
            upgradeTable.setVisible(false);
        }
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

        for (Structure coin : coins) {
            coin.draw(batch);
        }

        for (Structure structure : structuresOnMap) {
            structure.draw(batch);
        }
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
}
