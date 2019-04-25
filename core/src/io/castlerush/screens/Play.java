package io.castlerush.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import java.util.concurrent.ThreadLocalRandom;
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
import io.castlerush.structures.StructureCastle;
import io.castlerush.structures.StructureLoader;

public class Play implements Screen {

    // Player erstellen
    private Player player;

    public Batch batch;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private KeyListener keyListener;
    private String username;
    private TextButtonStyle textButtonStyle;
    private Stage stage;
    private Skin mySkin;

    // GUI
    private Dialog dialog;
    private Dialog inventory;
    public Table tableInventory;
    public TextButton buttonShop;
    public TextButton buttonExit;
    public InputMultiplexer inputMulti = new InputMultiplexer();
    public TextButton buttonBuy;
    public Image weaponImageSlot0;
    public Image transparentImageSlot1;
    public Image transparentImageSlot2;
    public Image transparentImageSlot3;
    public boolean shopIsOpen = false;

    // Structures on map and items
    public List<Structure> structuresOnMap = new ArrayList<Structure>();
    public List<Item> itemsInInventory = new ArrayList<Item>();

    public Play(String username) {
        this.username = username;

        // Load ressources
        ItemLoader.loadItems();
        StructureLoader.loadStructures();
    }

    @Override
    public void show() {

        map = new TmxMapLoader().load("maps/maps.tmx");

        renderer = new OrthogonalTiledMapRenderer(map);
        shapeRenderer = new ShapeRenderer();
        batch = renderer.getBatch();

        camera = new OrthographicCamera();

        camera.zoom = 1 / 3f;
        player = new Player("Markus", (new Sprite(new Texture("img/player.png"))), 100, 100, true,
                map, this);

        int randomX = ThreadLocalRandom.current().nextInt(16,
                (map.getProperties().get("width", Integer.class) - 3) * 16);
        int randomY = ThreadLocalRandom.current().nextInt(16,
                (map.getProperties().get("height", Integer.class) - 3) * 16);

        player.setX(randomX);
        player.setY(randomY);

        // Informations
        stage = new Stage();

        createHud();
        createItembar(stage);
        Shop.createShop(mySkin, stage, player, this);
        createButtonListener();

        inputMulti.addProcessor(stage);
        inputMulti.addProcessor(player.keyListener);
        Gdx.input.setInputProcessor(inputMulti);
    }

    // Erstellt ein Listener f�r alle Buttons
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

    private void createItembar(Stage stage) {
        // Create Table
        tableInventory = new Table(mySkin);
        tableInventory.setWidth(241);
        tableInventory.setHeight(58);
        tableInventory.setBackground(new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal("img/itembar.png")))));
        tableInventory.setPosition(Gdx.graphics.getWidth() / 2 - tableInventory.getWidth() / 2, 0);

        // transparent image
        weaponImageSlot0 = new Image(
                new Texture(Gdx.files.internal("weapons/fist.png")));
        transparentImageSlot1 = new Image(
                new Texture(Gdx.files.internal("img/transparent.png")));
        transparentImageSlot2 = new Image(
                new Texture(Gdx.files.internal("img/transparent.png")));
        transparentImageSlot3 = new Image(
                new Texture(Gdx.files.internal("img/transparent.png")));

        // Add components to table
        tableInventory.add(weaponImageSlot0).width(48).expandX();
        tableInventory.add(transparentImageSlot1).width(48).expandX();
        tableInventory.add(transparentImageSlot2).width(48).expandX();
        tableInventory.add(transparentImageSlot3).width(48).expandX();
        
        // Add table to dialog
        stage.addActor(tableInventory);
    }

    private void createHud() {

        // Label title
        mySkin = new Skin(Gdx.files.internal("skins/glassy-ui.json"));
        Label gameTitle = new Label("Name: " + username + "\nCoins: " + player.coins, mySkin);
        gameTitle.setSize(100, 100);
        gameTitle.setPosition(20, Gdx.graphics.getHeight() - gameTitle.getHeight() / 2 - 30);
        gameTitle.setAlignment(Align.left);
        BitmapFont font = new BitmapFont();

        // Button Spiel beenden
        buttonExit = new TextButton("Spiel beenden (ESC)", mySkin, "small");
        buttonExit.setName("buttonExit");
        buttonExit.setPosition(Gdx.graphics.getWidth() - 20 - buttonExit.getWidth(),
                Gdx.graphics.getHeight() - gameTitle.getHeight() / 2 - 20);

        // Button Shop �ffnen
        buttonShop = new TextButton("Shop oeffnen (E)", mySkin, "small");
        buttonShop.setName("buttonShop");
        buttonShop.setPosition(Gdx.graphics.getWidth() - 45 - buttonShop.getWidth(),
                Gdx.graphics.getHeight() - buttonExit.getHeight() - 80);
        buttonShop.setWidth(buttonExit.getWidth());

        // Itembar anzeigen
        Texture itembar = new Texture(Gdx.files.internal("img/itembar.png"));
        Image item = new Image(itembar);
        item.setSize(itembar.getWidth(), itembar.getHeight());
        item.setPosition(Gdx.graphics.getWidth() / 2 - item.getWidth() / 2, 0);

        // Herz f�r die Lebenspunkte hinzuf�gen
        Texture texture = new Texture(Gdx.files.internal("img/heart.png"));
        Image heart = new Image(texture);
        heart.setSize(texture.getWidth() / 6, texture.getHeight() / 6);
        heart.setPosition(Gdx.graphics.getWidth() - heart.getWidth() / 2 - 20, 20);

        // Anzeige der Lebenspunkte hinzuf�gen
        Label heartTitle = new Label("" + player.getHealth(), mySkin);
        heartTitle.setSize(100, 100);
        heartTitle.setPosition(
                Gdx.graphics.getWidth() - heart.getWidth() / 2 - heart.getWidth() - 20, 0);
        heartTitle.setAlignment(Align.left);
        BitmapFont font1 = new BitmapFont();

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
        Label archeryLabel = new Label("Bogensch�tzenturm", mySkin);
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
        stage.addActor(heart);
        stage.addActor(heartTitle);
        // stage.addActor(item);
        stage.addActor(buttonShop);
        stage.addActor(buttonExit);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(100 / 255f, 155 / 255f, 255 / 255f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(player.getX() + player.getWidth() / 2,
                player.getY() + player.getHeight() / 2, 0);
        camera.update();

        renderer.setView(camera);
        renderer.render();

        player.setSize((map.getProperties().get("tilewidth", Integer.class)) * 2,
                map.getProperties().get("tileheight", Integer.class) * 2);

        batch.begin();
        shapeRenderer.begin(ShapeType.Filled);

        drawStructures();
        drawItems();
        player.draw(batch);

        shapeRenderer.end();
        batch.end();

        stage.act();
        stage.draw();
    }

    public void drawStructures() {

        for (Structure structure : structuresOnMap) {
            structure.draw(batch);
        }
    }
    
    public void drawItems() {
        
        int count = 0;
        for(Item item : itemsInInventory) {
            //tableInventory.add(new Image(new TextureRegionDrawable(new TextureRegion(item.getTexture())))).width(48).expandX();
            //tableInventory.getCells().get(count).getActor().
            //transparentImageSlot1.setDrawable(new TextureRegionDrawable(new TextureRegion(item.getTexture())));
            //count++;
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
}
