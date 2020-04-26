package hu.hdani1337.marancsicsdash.Actor;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyFixtureDef;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.ShapeType;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Blood extends OneSpriteStaticActor {
    //region AssetList
    public static final String BLOOD_TEXTURE = "pic/blood.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(BLOOD_TEXTURE);
    }
    //endregion
    //region Konstruktor
    public Blood(MyGame game, World world, Zsolti zsolti) {
        super(game, BLOOD_TEXTURE);
        setSize(0.15f,0.15f);
        setPosition(zsolti.getX() + (float) (Math.random()*zsolti.getWidth()), zsolti.getY() + (float) Math.random()*(zsolti.getHeight()*0.6f));
        setActorWorldHelper(new Box2DWorldHelper(world, this, ShapeType.Circle, new MyFixtureDef(), BodyDef.BodyType.DynamicBody));
    }
    //endregion
    //region Act metódusai
    @Override
    public void act(float delta) {
        super.act(delta);
        /**
         * Nullkezelés, ha a vércsepp nem látszódik akkor távolítsuk el a világból és a stageről
         * **/
        if(getStage() != null)
            if(getX() < -getWidth() || getX() > getStage().getViewport().getWorldHeight())
                remove();
    }
    //endregion
}
