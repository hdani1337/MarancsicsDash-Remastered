package hu.hdani1337.marancsicsdash.Actor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyContactListener;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyFixtureDef;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.WorldBodyEditorLoader;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;
import hu.hdani1337.marancsicsdash.Stage.BossStage;

public class MarancsicsBoss extends OneSpriteAnimatedActor {

    public static final String MARANCSICSBOSS_ATLAS = "atlas/marancsicsBoss.atlas";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(MARANCSICSBOSS_ATLAS);
    }

    private float speed;
    public short hp;

    public MarancsicsBoss(MyGame game, World world, WorldBodyEditorLoader loader) {
        super(game, MARANCSICSBOSS_ATLAS);
        setFps(15);
        setSize(getWidth()*0.018f, getHeight()*0.018f);
        setActorWorldHelper(new Box2DWorldHelper(world, this, loader, "MarancsicsBoss", new MyFixtureDef(), BodyDef.BodyType.DynamicBody));
        speed = 0.18f;
        hp = 250;

        if(getActorWorldHelper() != null && getActorWorldHelper() instanceof Box2DWorldHelper) {
            ((Box2DWorldHelper) getActorWorldHelper()).addContactListener(new MyContactListener() {

                @Override
                public void beginContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {
                    if(otherHelper.getActor() instanceof Zsolti){
                        if(otherHelper.getActor().getY() < myHelper.getActor().getY()+myHelper.getActor().getHeight()/2){
                            Zsolti.isDead = true;
                            BossStage.isAct = false;
                        }else{
                            otherHelper.getBody().applyForceToCenter(new Vector2(0,2000),true);
                            hp -= (byte)(Math.random()*15);
                        }
                    }
                }

                @Override
                public void endContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {

                }

                @Override
                public void preSolve(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {

                }

                @Override
                public void postSolve(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {

                }
            });
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(getStage() != null && getStage() instanceof BossStage) {
            setX(getX() - speed);
            if(getX() < -getWidth() && !Zsolti.isDead) newPosition();
            if(hp <= 0) hp = 0;
        }
    }

    public void newPosition() {
        if(getStage() != null && getStage() instanceof BossStage) {
            setPosition((float) (Math.random() * 16 + getStage().getViewport().getWorldWidth()), Background.ground*2);
            speed = (float) (Math.random()/4)+0.15f;
        }
    }
}
