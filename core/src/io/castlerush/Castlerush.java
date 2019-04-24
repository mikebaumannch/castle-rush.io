package io.castlerush;

import java.util.concurrent.TimeUnit;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import io.castlerush.screens.Menu;
import io.castlerush.screens.Play;
import io.castlerush.screens.SplashScreen;

public class Castlerush extends Game {
    SpriteBatch batch;

	
	@Override
	public void create () {
	    
	    float delay = 5; // seconds

	    Timer.schedule(new Task(){
	        @Override
	        public void run() {
	            setScreen(new Play());
	        }
	    }, delay);
	    setScreen(new Menu());	
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
