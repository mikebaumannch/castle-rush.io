package io.castlerush.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class KeyListener implements InputProcessor {
    
    private OrthographicCamera camera;
    public boolean keyPressed = false;
    
    public KeyListener(OrthographicCamera camera){
        this.camera = camera;
        Gdx.input.setInputProcessor(this);
        
        
    }
    
    void handleInput() {
        if(keyPressed) {
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                camera.translate(-2,0);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                camera.translate(2,0);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
                camera.translate(0,-2);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                camera.translate(0,2);
            }
        }
    }
    
    @Override
    public boolean keyUp(int keycode) {
        keyPressed = false;
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        keyPressed = true;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }

}
