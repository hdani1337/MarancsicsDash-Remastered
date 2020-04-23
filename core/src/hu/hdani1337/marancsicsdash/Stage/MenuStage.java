package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;
import hu.hdani1337.marancsicsdash.MarancsicsDash;
import hu.hdani1337.marancsicsdash.SoundManager;
import hu.hdani1337.marancsicsdash.HudActors.Logo;
import hu.hdani1337.marancsicsdash.HudActors.TextBox;
import hu.hdani1337.marancsicsdash.Screen.GameScreen;
import hu.hdani1337.marancsicsdash.Screen.InfoScreen;
import hu.hdani1337.marancsicsdash.Screen.OptionsScreen;
import hu.hdani1337.marancsicsdash.Screen.ShopScreen;

import static hu.hdani1337.marancsicsdash.MarancsicsDash.UpdatePresence;
import static hu.hdani1337.marancsicsdash.SoundManager.hee;
import static hu.hdani1337.marancsicsdash.SoundManager.menuMusic;
import static hu.hdani1337.marancsicsdash.SoundManager.uraim;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;

public class MenuStage extends PrettyStage {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(Logo.class, assetList);
        assetList.collectAssetDescriptor(TextBox.class, assetList);
        SoundManager.load(assetList);
    }

    public MenuStage(MyGame game) {
        super(new ResponseViewport(900), game);
    }


    private Logo logo;
    private TextBox start;
    private TextBox info;
    private TextBox shop;
    private TextBox options;
    private TextBox exit;
    private TextBox version;

    @Override
    public void assignment() {
        SoundManager.assign();
        MarancsicsDash.presenceDetail = "Idling in the menu";
        UpdatePresence();
        logo = new Logo(game, Logo.LogoType.MENU);
        start = new TextBox(game ,"A játék indítása",1.25f);
        info = new TextBox(game, "A játékról",1.25f);
        shop = new TextBox(game, "Bolt",1.25f);
        options = new TextBox(game, "Beállítások",1.25f);
        exit = new TextBox(game, "Kilépés",1.25f);
        version = new TextBox(game, "Verzió: 2.0 Epsilon");
    }

    @Override
    public void setSizes() {
        version.setWidth(version.getWidth()*0.9f);
        start.setWidth(start.getWidth()*0.95f);
    }

    @Override
    public void setPositions() {
        logo.setPosition(getViewport().getWorldWidth()/2-logo.getWidth()/2,getViewport().getWorldHeight()-logo.getHeight()*1.25f);

        start.setX(getViewport().getWorldWidth()/2 - start.getWidth()/2);
        start.setY(getViewport().getWorldHeight()*0.55f - start.getHeight()/2);

        info.setY(start.getY() - info.getHeight()*1.5f);
        info.setX((getViewport().getWorldWidth()/2 - info.getWidth()/2));

        shop.setY(info.getY() - shop.getHeight()*1.5f);
        shop.setX((getViewport().getWorldWidth()/2 - shop.getWidth()/2));

        options.setY(shop.getY() - options.getHeight()*1.5f);
        options.setX((getViewport().getWorldWidth()/2 - options.getWidth()/2));

        exit.setY(options.getY() - exit.getHeight()*1.5f);
        exit.setX((getViewport().getWorldWidth()/2 - exit.getWidth()/2));

        version.setX(40);
        version.setY(30);
    }

    @Override
    public void addListeners() {
        start.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fadeOut = true;
                if(!muted) uraim.play(1);
                addTimer(new TickTimer(0.3f, false, new TickTimerListener(){
                    @Override
                    public void onTick(Timer sender, float correction) {
                        super.onTick(sender, correction);
                        game.setScreenWithPreloadAssets(GameScreen.class, new LoadingStage(game));
                    }
                }));
            }
        });

        info.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fadeOut = true;
                addTimer(new TickTimer(0.3f, false, new TickTimerListener(){
                    @Override
                    public void onTick(Timer sender, float correction) {
                        super.onTick(sender, correction);
                        game.setScreenWithPreloadAssets(InfoScreen.class, new LoadingStage(game));
                    }
                }));
            }
        });

        shop.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fadeOut = true;
                addTimer(new TickTimer(0.3f, false, new TickTimerListener(){
                    @Override
                    public void onTick(Timer sender, float correction) {
                        super.onTick(sender, correction);
                        game.setScreenWithPreloadAssets(ShopScreen.class, new LoadingStage(game));
                    }
                }));
            }
        });

        options.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fadeOut = true;
                addTimer(new TickTimer(0.3f, false, new TickTimerListener(){
                    @Override
                    public void onTick(Timer sender, float correction) {
                        super.onTick(sender, correction);
                        game.setScreenWithPreloadAssets(OptionsScreen.class, new LoadingStage(game));
                    }
                }));
            }
        });

        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!muted){
                    hee.play(1);
                }
                addTimer(new TickTimer(0.5f,false,new TickTimerListener(){
                    @Override
                    public void onStop(Timer sender) {
                        super.onStop(sender);
                        Gdx.app.exit();
                    }
                }));
            }
        });
    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        setAlpha(0);
        addActor(logo);
        addActor(start);
        addActor(info);
        addActor(shop);
        addActor(options);
        addActor(exit);
        addActor(version);
    }

    @Override
    public void afterInit() {
        if(!muted) {
            menuMusic.setLooping(true);
            menuMusic.setVolume(0.7f);
            menuMusic.play();
        }
    }

    private void setAlpha(float alpha){
        logo.setAlpha(alpha);
        start.setAlpha(alpha);
        info.setAlpha(alpha);
        shop.setAlpha(alpha);
        options.setAlpha(alpha);
        exit.setAlpha(alpha);
        version.setAlpha(alpha);
    }

    float alpha = 0;
    boolean fadeOut = false;
    boolean fadeIn = true;
    float time = 0;

    @Override
    public void act(float delta) {
        super.act(delta);
        if(fadeIn) {
            if (alpha < 0.95) {
                alpha += 0.05f;
                setAlpha(alpha);
            }
            else {
                alpha = 1;
                setAlpha(alpha);
                fadeIn = false;
            }
        }

        if(fadeOut) {
            time += delta;
            if (alpha > 0.05f) {
                alpha -= 0.05f;
                setAlpha(alpha);
            }
            else {
                alpha = 0;
                setAlpha(alpha);
                fadeOut = false;
            }
        }
    }
}
