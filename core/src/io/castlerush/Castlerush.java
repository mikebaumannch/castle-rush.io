package io.castlerush;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.castlerush.screens.Play;

public class Castlerush extends Game {
	
	@Override
	public void create () {
	    setScreen(new Play());
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
