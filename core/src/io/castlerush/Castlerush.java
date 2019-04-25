package io.castlerush;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.castlerush.screens.Menu;
import io.castlerush.screens.SplashScreen;

public class Castlerush extends Game {

    private Game game;
    public Viewport screenPort;

    // Konstruktor
    public Castlerush() {
        
        /*
        Pixmap pm = new Pixmap(Gdx.files.internal("img/cursor.jpg"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
        pm.dispose();*/
        
        game = this;
    }

    @Override
    public void create() {

        screenPort = new ScreenViewport();
        float delay = 5;

        Timer.schedule(new Task() {
            @Override
            public void run() {
                setScreen(new Menu(game));
            }
        }, delay);
        setScreen(new SplashScreen());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public void resize(int width, int height) {
        super.resize(width, height);
    }

    public void pause() {
        super.pause();
    }

    public void resume() {
        super.resume();
    }
}
