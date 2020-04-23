package hu.hdani1337.marancsicsdash.Actor;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyFixtureDef;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.WorldBodyEditorLoader;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;
import hu.hdani1337.marancsicsdash.Stage.GameStage;

import static hu.hdani1337.marancsicsdash.Actor.Background.ground;

public class SuperCoin extends OneSpriteAnimatedActor implements CollectableItem{

    public static final String SUPERCOIN_TEXTURE = "atlas/superCoin.atlas";

    private boolean isAct;
    private Zsolti zsolti;
    private GameStage stage;

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(SUPERCOIN_TEXTURE);
    }

    /**
     * Scene2D konstruktor, mozog
     * **/
    public SuperCoin(MyGame game) {
        this(game,true);
    }

    /**
     * Scene2D konstruktor
     * @param isAct mozogjon e a pénz
     * **/
    public SuperCoin(MyGame game, boolean isAct) {
        super(game, SUPERCOIN_TEXTURE);
        this.isAct = isAct;
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

    /**
     * Új pozíció beállítása, az értékek még nincsenek tesztelve
     * **/
    public void newPosition(){
        float newY = (float)(Math.random() * 6 + ground);
        float newX = (float)(Math.random() * 96 + 168);
        setPosition(newX,newY);
    }

    /**
     * Mozgás utólagos ki-be kapcsolása
     * **/
    public void setAct(boolean newAct){
        this.isAct = newAct;
    }

    @Override
    public synchronized void act(float delta) {
        super.act(delta);
        if(GameStage.isAct) {
            isCollected(zsolti);
            if (getX() < 0 - getWidth()) newPosition();//Ha kiér a képből akkor új pozíciót kap
        }
    }

    @Override
    public void collected() {
        stage.addCoins();
        newPosition();
    }
}
