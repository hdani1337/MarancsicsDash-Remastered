package hu.hdani1337.marancsicsdash.Actor;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyFixtureDef;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.WorldBodyEditorLoader;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;

import static hu.hdani1337.marancsicsdash.Actor.Background.ground;

public class SuperCoin extends OneSpriteAnimatedActor {

    public static final String SUPERCOIN_TEXTURE = "atlas/superCoin.atlas";

    private boolean isAct;

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
     * Box2D konstruktor
     * **/
    public SuperCoin(MyGame game, World world, WorldBodyEditorLoader loader) {
        super(game, SUPERCOIN_TEXTURE);
        setSize(getWidth()*0.011f, getHeight()*0.011f);
        setActorWorldHelper(new Box2DWorldHelper(world, this, loader, "Coin", new MyFixtureDef(), BodyDef.BodyType.StaticBody));
        newPosition();
        setFps(75);
    }

    /**
     * Új pozíció beállítása, az értékek még nincsenek tesztelve
     * **/
    public void newPosition(){
        float newY = (float)(Math.random() * 3 + ground);
        float newX = (float)(Math.random() * 48 + 24);
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
        if(isAct) {
            if (getX() < 0 - getWidth()) newPosition();//Ha kiér a képből akkor új pozíciót kap
            setX(getX() - 0.5f);
        }
    }
}
