package hu.hdani1337.marancsicsdash;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.hdani1337.marancsicsdash.Screen.IntroScreen;
import hu.hdani1337.marancsicsdash.Stage.LoadingStage;

public class MarancsicsDash extends MyGame {
	//region Konstruktorok
	public MarancsicsDash() {
	}

	public MarancsicsDash(boolean debug) {
		super(debug);
	}
	//endregion
	//region Változók
	public static boolean needsLoading;//Kell e a töltőképernyőre grafika
	public static Preferences preferences;//Mentés
	public static boolean muted;//Némítva van e a játék
	public static String presenceDetail;
	private static long startTime;
	//endregion
	//region Create metódus
	@Override
	public void create() {
		super.create();
		loadPreferences();
		setLoadingStage(new LoadingStage(this));
		setScreen(new IntroScreen(this));
		SoundManager.game = this;
		setDiscordRichPresence();
	}
	//endregion
	//region Mentés betöltő metódus
	/**Próbáljuk meg betölteni a mentést
	 * Állítsuk be a felbontást és a teljes képernyős módot
	 * **/
	private static void loadPreferences(){
		needsLoading = false;//Először az Intro fog bejönni, oda legyen üres a töltőképernyő
		try {
			preferences = Gdx.app.getPreferences("marancsicsDashSave");
			muted = preferences.getBoolean("muted");
			setDisplay();
		}catch (NullPointerException e){
			/**Ha NullPointert kapunk, akkor még nincsenek mentett adatok**/
		}
	}
	//endregion
	//region Discord Rich Presence metódusai
	private static void setDiscordRichPresence(){
		if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
			startTime = System.currentTimeMillis() / 1000l;
			presenceDetail = "Starting the game...";
			UpdatePresence();
		}
	}

	private static void setDisplay(){
		if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
			if (preferences.getInteger("windowWidth") != 0 && preferences.getInteger("windowHeight") != 0)
				Gdx.graphics.setWindowedMode(preferences.getInteger("windowWidth"), preferences.getInteger("windowHeight"));
			else Gdx.graphics.setWindowedMode(1280, 720);
			if (preferences.getBoolean("fullscreen"))
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		}
	}

	public static void UpdatePresence()
	{
		if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
			DiscordRPC lib = DiscordRPC.INSTANCE;
			String applicationId = "700046583047127061";
			DiscordEventHandlers handlers = new DiscordEventHandlers();
			lib.Discord_Initialize(applicationId, handlers, true, "");

			DiscordRichPresence presence = new DiscordRichPresence();
			presence.startTimestamp = startTime; // epoch second
			presence.details = presenceDetail;
			presence.largeImageKey = "ic_launcher-web";
			presence.largeImageText = "v2.0-Epsilon";
			lib.Discord_UpdatePresence(presence);

			new Thread(() -> {
				while (!Thread.currentThread().isInterrupted()) {
					lib.Discord_RunCallbacks();
					try {
						Thread.sleep(500);
					} catch (InterruptedException ignored) {
					}
				}
			}, "RPC-Callback-Handler").start();
		}
	}
	//endregion
}
