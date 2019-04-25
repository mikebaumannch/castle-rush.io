package io.castlerush;

import java.util.concurrent.TimeUnit;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.castlerush.screens.Menu;
import io.castlerush.screens.Play;
import io.castlerush.screens.SplashScreen;

public class Castlerush extends Game {
    
    private Game game;
    public Viewport screenPort;
    
    //Konstruktor
    public Castlerush() {
        game = this;
    }
	
	@Override
	public void create () {
	    
	    screenPort = new ScreenViewport();
	    float delay = 5;

	    Timer.schedule(new Task(){
	        @Override
	        public void run() {
	            setScreen(new Menu(game));
	        }
	    }, delay);
	    setScreen(new SplashScreen());	
	}

	@Override
	public void render () {
	    super.render();
	}
	
	@Override
	public void dispose () {
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
