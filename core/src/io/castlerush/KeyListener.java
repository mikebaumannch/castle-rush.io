package io.castlerush;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class KeyListener implements InputProcessor {
    
    private OrthographicCamera camera;
    public boolean walkUp = false;
    public boolean walkDown = false;
    public boolean walkRight = false;
    public boolean walkLeft = false;
    
    public KeyListener(OrthographicCamera camera) {
        this.camera = camera;
        Gdx.input.setInputProcessor(this);
    }
    
    public void handleInput() {
        
        if(walkLeft) {
            camera.translate(-2,0);
        }
        if(walkRight) {
            camera.translate(2,0);
        }
        if(walkUp) {
            camera.translate(0,2);
        }
        if(walkDown) {
            camera.translate(0,-2);
        }
        
    }

    @Override
    public boolean keyDown(int keycode) {
        
        switch(keycode) {
        
        case Input.Keys.UP:
            walkUp = true;   
            break;
        
        case Input.Keys.DOWN:
            walkDown = true;   
            break;
        
        case Input.Keys.RIGHT:
            walkRight = true;   
            break;
        
        case Input.Keys.LEFT:
            walkLeft = true;   
            break;
            
        }
        
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        
        walkUp = false;
        walkDown = false;
        walkLeft = false;
        walkRight = false;
        
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
