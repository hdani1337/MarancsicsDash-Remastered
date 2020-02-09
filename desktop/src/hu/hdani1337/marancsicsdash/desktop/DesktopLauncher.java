package hu.hdani1337.marancsicsdash.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import hu.hdani1337.marancsicsdash.MarancsicsDash;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MarancsicsDash(), config);
		config.width = 1600;
		config.height = 900;
	}
}
