package hu.hdani1337.marancsicsdash.Actor;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;
import hu.hdani1337.marancsicsdash.Stage.GameStage;

import static hu.hdani1337.marancsicsdash.Actor.Background.ground;

public class SuperCoin extends OneSpriteAnimatedActor implements CollectableItem{
    //region AssetList
    public static final String SUPERCOIN_TEXTURE = "atlas/superCoin.atlas";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(SUPERCOIN_TEXTURE);
    }
    //endregion
    //region Változók
    private Zsolti zsolti;
    private GameStage stage;
    //endregion
    //region Konstruktorok
    /**
     * Scene2D konstruktor
     * **/
    public SuperCoin(MyGame game) {
        super(game, SUPERCOIN_TEXTURE);
        setFps(75);
        setSize(100,100);
    }

    /**
     * GameStage konstruktor
     * **/
    public SuperCoin(MyGame game, GameStage stage) {
        super(game, SUPERCOIN_TEXTURE);
        setSize(getWidth()*0.011f, getHeight()*0.011f);
        newPosition();
        setFps(75);
        this.stage = stage;
        this.zsolti = stage.zsolti;
    }
    //endregion
    //region Act metódusai
    @Override
    public synchronized void act(float delta) {
        super.act(delta);
        if(GameStage.isAct) {
            if(zsolti!=null) {
                isCollected(zsolti);
                if (getX() < 0 - getWidth()) newPosition();//Ha kiér a képből akkor új pozíciót kap
            }
        }
    }

    @Override
    public void collected() {
        stage.addCoins();
        newPosition();
    }

    /**
     * Új pozíció beállítása, az értékek még nincsenek tesztelve
     * **/
    public void newPosition(){
        float newY = (float)(Math.random() * 6 + ground);
        float newX = (float)(Math.random() * 96 + 168);
        setPosition(newX,newY);
    }
    //endregion
}
