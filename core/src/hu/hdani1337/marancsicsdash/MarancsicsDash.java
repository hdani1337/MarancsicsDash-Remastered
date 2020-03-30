package hu.hdani1337.marancsicsdash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.hdani1337.marancsicsdash.Screen.IntroScreen;
import hu.hdani1337.marancsicsdash.Stage.LoadingStage;

public class MarancsicsDash extends MyGame {

	public MarancsicsDash() {
	}

	public MarancsicsDash(boolean debug) {
		super(debug);
	}

	public static boolean needsLoading;//Kell e a töltőképernyőre grafika
	public static Preferences preferences;//Mentés
	public static boolean muted;//Némítva van e a játék

	@Override
	public void create() {
		super.create();
		needsLoading = false;//Először az Intro fog bejönni, oda legyen üres a töltőképernyő

		/**Próbáljuk meg betölteni a mentést**/
		try {
			preferences = Gdx.app.getPreferences("marancsicsDashSave");
			muted = preferences.getBoolean("muted");
		}catch (NullPointerException e){
			/**Ha NullPointert kapunk, akkor még nincsenek mentett adatok**/
		}
		setLoadingStage(new LoadingStage(this));
		setScreen(new IntroScreen(this));
		SoundManager.game = this;
	}
}
