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

import static hu.hdani1337.marancsicsdash.MarancsicsDash.preferences;

public class Coin extends OneSpriteAnimatedActor {

    public static final String COIN_ATLAS = "atlas/coin.atlas";
    public static long coin = preferences.getLong("coin");

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(COIN_ATLAS);
    }

    private boolean isAct;

    /**
     * Box2D konstruktor
     * **/
    public Coin(MyGame game, World world, WorldBodyEditorLoader loader, GameStage stage) {
        super(game, COIN_ATLAS);
        setFps(60);
        setSize(getWidth()*0.006f, getHeight()*0.006f);
        setActorWorldHelper(new Box2DWorldHelper(world, this, loader, "Coin", new MyFixtureDef(), BodyDef.BodyType.StaticBody));
        setX((float) (stage.getViewport().getWorldWidth()+Math.random()*9));
        setY((float) (Background.ground+0.2));
        this.isAct = true;
    }

    /**
     * Scene2D konstruktor
     * @param act forogjon e a pénz
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
        if(this.isAct) {
            setX(getX() - 0.15f);
        }
    }
}
