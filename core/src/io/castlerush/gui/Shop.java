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

public class Shop {
    
    private static Stage stage;
    private static Play play;
    private static Dialog dialog;
    private static Label title;
    private static Label nameInformation;
    private static Label priceInformation;
    private static Label wallLabel;
    private static Label wallPrice;
    private static TextButton wallButton;
    private static Label archeryLabel;
    private static Label archeryPrice;
    private static TextButton archeryButton;
    private static Label trapLabel;
    private static Label trapPrice;
    private static TextButton trapButton;
    private static Label swordLabel;
    private static Label swordPrice;
    private static TextButton swordButton;
    private static Label slingshotLabel;
    private static Label slingshotdPrice;
    private static TextButton slingshotButton;
    
    
  //Erstellt den Shop
    public static void createShop(Skin mySkin, Stage stage, final Player player, final Play play) {
        
        Shop.stage = stage;
        Shop.play = play;
        
        //Dialog
        dialog = new Dialog("", mySkin, "default");
        
        //Create Components of table
        //Title
        title = new Label("Shop", mySkin, "big");
        //Informations
        nameInformation = new Label("Name:", mySkin);
        priceInformation = new Label("Preis:", mySkin);
        //Wall
        wallLabel = new Label("Holzmauer", mySkin);
        wallPrice = new Label("50", mySkin);
        wallButton = new TextButton("Kaufen",mySkin, "small");
        //Archery
        archeryLabel = new Label("Bogenschützenturm", mySkin);
        archeryPrice = new Label("200", mySkin);
        archeryButton = new TextButton("Kaufen",mySkin, "small");
        //Trap
        trapLabel = new Label("Falle", mySkin);
        trapPrice = new Label("200", mySkin);
        trapButton = new TextButton("Kaufen",mySkin, "small");
        //Sword
        swordLabel = new Label("Holzschwert", mySkin);
        swordPrice = new Label("200", mySkin);
        swordButton = new TextButton("Kaufen",mySkin, "small");
        //Slingshot
        slingshotLabel = new Label("Slingshot", mySkin);
        slingshotdPrice = new Label("200", mySkin);
        slingshotButton = new TextButton("Kaufen",mySkin, "small");
        
        //Create Table
        Table table = new Table();
        
        //Add Components to table
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
        dialog.setPosition(Gdx.graphics.getWidth()/2-dialog.getWidth()/2, Gdx.graphics.getHeight()/2-dialog.getHeight()/2);
        
        //Settings
        title.setPosition(dialog.getWidth()/2 - title.getWidth()/2, dialog.getHeight());
       
        //Add table to dialog
        dialog.addActor(title);
        dialog.addActor(table);
        
        table.setPosition(dialog.getWidth()/2 - table.getWidth()/2, 210);
        
        wallButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //Aktion für Wall kaufen
                player.buy(ItemLoader.woodWall);
            }
        });
        
        archeryButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //Aktion für Bogenschützenturm kaufen
                player.buy(ItemLoader.towerLvl1);
            }
        });
        
        trapButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //Aktion für Schwert kaufen
                player.buy(ItemLoader.woodSword);
            }
        });
        
        swordButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //Aktion für Slingshot kaufen
                player.buy(ItemLoader.slingShot);
            }
        });
        
        slingshotButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //Aktion für Falle kaufen
                player.buy(ItemLoader.trap);
            }
        });
        
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                
                if(keycode == Input.Keys.E && play.shopIsOpen) {
                    play.shopIsOpen = false;
                    closeShop();
                }
                
                return false;
            }
        });
    }
    
    public static void showShop() {
        dialog.show(stage);
        dialog.setWidth(400);
        dialog.setHeight(500);
        dialog.setPosition(Gdx.graphics.getWidth()/2-dialog.getWidth()/2, Gdx.graphics.getHeight()/2-dialog.getHeight()/2);
        stage.addActor(dialog);
        play.shopIsOpen = true;
    }
    
    public static void closeShop() {
        dialog.hide();
    }

}
