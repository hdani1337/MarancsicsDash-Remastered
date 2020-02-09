package hu.hdani1337.marancsicsdash.Actor;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyFixtureDef;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.WorldBodyEditorLoader;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Mushroom extends OneSpriteStaticActor {

    public static final String MUSHROOM_TEXTURE = "pic/mushroom.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(MUSHROOM_TEXTURE);
    }

    public Mushroom(MyGame game, World world, WorldBodyEditorLoader loader) {
        super(game, MUSHROOM_TEXTURE);
        setSize(getWidth()*0.003f, getHeight()*0.003f);
        setActorWorldHelper(new Box2DWorldHelper(world, this, loader, "Mushroom", new MyFixtureDef(), BodyDef.BodyType.StaticBody));
    }
}
