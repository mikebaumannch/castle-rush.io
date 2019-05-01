package io.castlerush.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.castlerush.Castlerush;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		//Options
		config.title = "CastleRush.io";
		config.useGL30 = true;
		config.width = 1280;
		config.height = 720;
		config.resizable = false;
		//config.fullscreen = true;
		new LwjglApplication(new Castlerush(), config);
	}
}
