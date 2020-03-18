package hu.hdani1337.marancsicsdash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.hdani1337.marancsicsdash.Screen.IntroScreen;
import hu.hdani1337.marancsicsdash.Screen.MenuScreen;
import hu.hdani1337.marancsicsdash.Screen.ShopScreen;
import hu.hdani1337.marancsicsdash.Stage.LoadingStage;

public class MarancsicsDash extends MyGame {

	public MarancsicsDash() {
	}

	public MarancsicsDash(boolean debug) {
		super(debug);
	}

	public static boolean needsLoading;
	public static Preferences preferences;
	public static boolean muted;

	@Override
	public void create() {
		super.create();
		needsLoading = false;
		try {
			preferences = Gdx.app.getPreferences("marancsicsDashSave");
			muted = preferences.getBoolean("muted");
		}catch (NullPointerException e){

		}
		setLoadingStage(new LoadingStage(this));
		setScreen(new IntroScreen(this));
	}
}
