package io.castlerush.gui;

import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import io.castlerush.Player;
import io.castlerush.items.Item;
import io.castlerush.items.ItemLoader;
import io.castlerush.screens.Play;
import io.castlerush.structures.StructureLoader;

public class Shop {

    // STAGE
    private static Stage stage;
    private static Play play;

    // GUI ELEMENTS
    private static Dialog upgradeDialog, dialog;
    private static Label title, upgrageTitle, nameInformation, priceInformation, wallLabel,
            wallPrice, archeryLabel, archeryPrice, trapLabel, trapPrice, swordLabel, swordPrice,
            slingshotLabel, slingshotdPrice;
    private static TextButton wallButton;
    private static TextButton archeryButton;
    private static TextButton trapButton;
    private static TextButton swordButton;
    private static TextButton slingshotButton;
    
    // UTIL
    private static String direction = "LEFT";

    // Erstellt den Shop
    public static void createShop(Skin mySkin, Stage stage, final Player player, final Play play) {

        Shop.stage = stage;
        Shop.play = play;
        
        // Shop dialog
        dialog = new Dialog("", mySkin, "default");

        // Create Components of table
        // Title
        title = new Label("Shop", mySkin, "big");

        // Informations
        nameInformation = new Label("Name:", mySkin);
        priceInformation = new Label("Preis:", mySkin);
        // Wall
        wallLabel = new Label("Holzmauer", mySkin);
        wallPrice = new Label("OUT OF SALE", mySkin);
        wallButton = new TextButton("Kaufen", mySkin, "small");
        // Archery
        archeryLabel = new Label("Bogenschützenturm", mySkin);
        archeryPrice = new Label("" + new ItemLoader().towerLvl1.getPrice(), mySkin);
        archeryButton = new TextButton("Kaufen", mySkin, "small");
        // Trap
        trapLabel = new Label("Falle", mySkin);
        trapPrice = new Label("" + new ItemLoader().trap.getPrice(), mySkin);
        trapButton = new TextButton("Kaufen", mySkin, "small");
        // Sword
        swordLabel = new Label("Holzschwert", mySkin);
        swordPrice = new Label("" + new ItemLoader().woodSword.getPrice(), mySkin);
        swordButton = new TextButton("Kaufen", mySkin, "small");
        // Slingshot
        slingshotLabel = new Label("Slingshot", mySkin);
        slingshotdPrice = new Label("" + new ItemLoader().slingShot.getPrice(), mySkin);
        slingshotButton = new TextButton("Kaufen", mySkin, "small");

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
        dialog.setHeight(500);
        stage.addActor(dialog);

        // Settings
        title.setPosition(dialog.getWidth() / 2 - title.getWidth() / 2, dialog.getHeight() - 100);

        // Add table to dialog
        dialog.addActor(title);
        dialog.addActor(table);

        table.setPosition(dialog.getWidth() / 2 - table.getWidth() / 2, 210);
        
        // Listener
        play.buttonExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.exit(0);
            }
        });

        play.buttonShop.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Shop.showShop();
            }
        });

        wallButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                // Aktion für Wall kaufen
                Item shopItem = new ItemLoader().woodWall;
                if (player.getCoins() >= shopItem.getPrice()) {
                    player.buy(shopItem);
                }
            }
        });

        archeryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                // Aktion für Bogenschützenturm kaufen
                Item shopItem = new ItemLoader().towerLvl1;
                if (player.getCoins() >= shopItem.getPrice()) {
                    player.buy(shopItem);
                }
            }
        });

        trapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                // Aktion für Falle kaufen
                Item shopItem = new ItemLoader().trap;
                if (player.getCoins() >= shopItem.getPrice()) {
                    player.buy(shopItem);
                }

            }
        });

        swordButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                // Aktion für Schwert kaufen
                Item shopItem = new ItemLoader().woodSword;

                if (player.getCoins() >= shopItem.getPrice()) {
                    player.buy(shopItem);
                }
            }
        });

        slingshotButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                // Aktion für Slingshot kaufen
                Item shopItem = new ItemLoader().slingShot;
                if (player.getCoins() >= shopItem.getPrice()) {
                    player.buy(shopItem);
                }
            }
        });

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                if (keycode == Input.Keys.E) {
                    if(play.shopIsOpen) {
                        play.shopIsOpen = false;
                        closeShop();
                    }else {
                        play.shopIsOpen = true;
                        showShop();
                    }
                }
                
                if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
                    if (direction == "LEFT") {
                        player.flip(true, false);
                        direction = "RIGHT";
                    }
                }
                
                if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
                    if (direction == "RIGHT") {
                        player.flip(true, false);
                        direction = "LEFT";
                    }
                }

                return false;
            }
        });
    }
    
    public static void showShop() {
        dialog.show(stage);
        dialog.setWidth(400);
        dialog.setHeight(500);
        dialog.setPosition(Gdx.graphics.getWidth() / 2 - dialog.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - dialog.getHeight() / 2);
        stage.addActor(dialog);
        play.shopIsOpen = true;
    }

    public static void closeShop() {
        dialog.hide();
    }

}
