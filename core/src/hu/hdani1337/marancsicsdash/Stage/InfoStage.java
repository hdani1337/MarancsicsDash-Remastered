package hu.hdani1337.marancsicsdash.Stage;

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
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;
import hu.hdani1337.marancsicsdash.HudActors.Logo;
import hu.hdani1337.marancsicsdash.HudActors.TextBox;
import hu.hdani1337.marancsicsdash.MarancsicsDash;
import hu.hdani1337.marancsicsdash.SoundManager;

import static hu.hdani1337.marancsicsdash.MarancsicsDash.UpdatePresence;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;

public class InfoStage extends PrettyStage {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(TextBox.class, assetList);
        assetList.addTexture("pic/menuBg.jpg");
        SoundManager.load(assetList);
    }

    public InfoStage(MyGame game) {
        super(new ResponseViewport(900), game);
    }

    private Logo infoLogo;

    private OneSpriteStaticActor bg;
    private TextBox back;
    private TextBox desc;

    private OneSpriteStaticActor en;
    private MyLabel name;
    private MyLabel title;

    private boolean setBack;

    @Override
    public void assignment() {
        SoundManager.assign();
        MarancsicsDash.presenceDetail = "Reading some good stuff";
        UpdatePresence();
        if(!muted)
            SoundManager.menuMusic.play();
        bg = new OneSpriteStaticActor(game,"pic/menuBg.jpg");
        back = new TextBox(game, "Vissza a menübe");
        desc = new TextBox(game, "A játék valós eseményeken alapul.\nEgy szép napon a föhösünk, Zsolti beszólt szeretett\nosztályfönökünknek, Marancsicsnak.\nMarancsics nagyon megharagudott rá, s mindenáron elakarja kapni Zsoltit,\nhogy osztályfönökit adhasson neki. A Te feladatod az,\nhogy Zsolti minél tovább tudjon menekülni. Vigyázz, mert Marancsics\nneked tudja rúgni az akadályokat!");
        infoLogo = new Logo(game, Logo.LogoType.INFO);
    }

    @Override
    public void setSizes() {
        if(getViewport().getWorldWidth() > bg.getWidth()) bg.setWidth(getViewport().getWorldWidth());
    }

    @Override
    public void setPositions() {
        if(getViewport().getWorldWidth() < bg.getWidth()) bg.setX((getViewport().getWorldWidth()-bg.getWidth())/2);
        desc.setPosition(getViewport().getWorldWidth()/2-desc.getWidth()/2,getViewport().getWorldHeight()/2-desc.getHeight()/2-20);
        back.setPosition(getViewport().getWorldWidth() - (back.getWidth() + 45),50);
        infoLogo.setPosition(getViewport().getWorldWidth()/2 - infoLogo.getWidth()/2, getViewport().getWorldHeight() - infoLogo.getHeight()*1.15f);
    }

    @Override
    public void addListeners() {
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                setBack = true;
            }
        });
    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(bg);
        addActor(infoLogo);
        addActor(back);
        addActor(desc);
    }

    float alpha = 0;

    @Override
    public void act(float delta) {
        super.act(delta);
        if(!setBack) {
            //Áttűnéssel jönnek be az actorok
            if (alpha < 0.95) alpha += 0.05;
            else alpha = 1;
            setAlpha();
        }
        else
        {
            //Áttűnéssel mennek ki az actorok
            if (alpha > 0.05) {
                setAlpha();
                alpha -= 0.05;
            } else {
                //Ha már nem látszanak akkor megyünk vissza a menübe
                alpha = 0;
                setAlpha();
                game.setScreenBackByStackPopWithPreloadAssets(new LoadingStage(game));
                addTimer(new TickTimer(1,false,new TickTimerListener(){
                    @Override
                    public void onTick(Timer sender, float correction) {
                        super.onTick(sender, correction);
                        setBack = false;
                    }
                }));
            }
        }
    }

    /**
     * Actorok átlátszóságának egyidejű beállítása
     * **/
    private void setAlpha(){
        infoLogo.setAlpha(alpha);
        back.setAlpha(alpha);
        desc.setAlpha(alpha);
    }
}
