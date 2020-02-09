package hu.hdani1337.marancsicsdash.Actor;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyFixtureDef;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.WorldBodyEditorLoader;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;

public class MarancsicsBoss extends OneSpriteAnimatedActor {

    public static final String MARANCSICSBOSS_ATLAS = "atlas/marancsicsBoss.atlas";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(MARANCSICSBOSS_ATLAS);
    }

    public MarancsicsBoss(MyGame game, World world, WorldBodyEditorLoader loader) {
        super(game, MARANCSICSBOSS_ATLAS);
        setFps(15);
        setSize(getWidth()*0.011f, getHeight()*0.011f);
        setActorWorldHelper(new Box2DWorldHelper(world, this, loader, "MarancsicsBoss", new MyFixtureDef(), BodyDef.BodyType.DynamicBody));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setX(getX()-0.05f);
    }
}
