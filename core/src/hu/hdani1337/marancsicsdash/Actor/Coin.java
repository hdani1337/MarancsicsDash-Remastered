package hu.hdani1337.marancsicsdash.Actor;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyFixtureDef;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.WorldBodyEditorLoader;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;

public class Coin extends OneSpriteAnimatedActor {

    public static final String COIN_ATLAS = "atlas/coin.atlas";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(COIN_ATLAS);
    }

    public Coin(MyGame game, World world, WorldBodyEditorLoader loader) {
        super(game, COIN_ATLAS);
        setFps(60);
        setSize(getWidth()*0.008f, getHeight()*0.008f);
        setActorWorldHelper(new Box2DWorldHelper(world, this, loader, "Coin", new MyFixtureDef(), BodyDef.BodyType.StaticBody));
    }
}
