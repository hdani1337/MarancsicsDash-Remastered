package hu.hdani1337.marancsicsdash.Actor;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;
import hu.hdani1337.marancsicsdash.Stage.GameStage;

import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.preferences;
import static hu.hdani1337.marancsicsdash.SoundManager.coinSound;

public class Coin extends OneSpriteAnimatedActor implements CollectableItem {

    public static final String COIN_ATLAS = "atlas/coin.atlas";
    public static long coin = preferences.getLong("coin");

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(COIN_ATLAS);
    }

    private boolean isAct;
    private Zsolti zsolti;

    /**
     * GameStage konstruktor
     * @param stage A GameStage, hogy hozzáférjünk Zsoltihoz
     * **/
    public Coin(MyGame game, GameStage stage) {
        super(game, COIN_ATLAS);
        setFps(60);
        setSize(getWidth()*0.006f, getHeight()*0.006f);
        setX((float) (stage.getViewport().getWorldWidth()+Math.random()*18));
        setY((float) ((stage.marancsics.getY()+stage.marancsics.getHeight()/2)+Math.random()*4.5f));
        this.isAct = true;
        this.zsolti = stage.zsolti;
    }

    /**
     * Scene2D konstruktor
     * @param act Forogjon e a pénz
     * **/
    public Coin(MyGame game, boolean act){
        super(game, COIN_ATLAS);
        this.isAct = false;
        if (act) {
            setFps(60);
        } else {
            setFps(0);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(this.isAct && GameStage.isAct) {
            isCollected(zsolti);
        }
    }

    @Override
    public void collected() {
        Coin.coin++;
        if (!muted) coinSound.play();
        remove();
    }
}
