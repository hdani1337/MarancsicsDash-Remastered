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
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;
import hu.hdani1337.marancsicsdash.Stage.GameStage;

public class Zsolti extends OneSpriteAnimatedActor {

    public static final String ZSOLTI_ATLAS = "atlas/zsolti.atlas";
    public static final String SUPER_ZSOLTI_ATLAS = "atlas/superZsolti.atlas";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(ZSOLTI_ATLAS);
        assetList.addTextureAtlas(SUPER_ZSOLTI_ATLAS);
    }

    public Zsolti(MyGame game, World world, WorldBodyEditorLoader loader) {
        super(game, ZSOLTI_ATLAS);
        setFps(12);
        setSize(getWidth()*0.011f, getHeight()*0.011f);
        setActorWorldHelper(new Box2DWorldHelper(world, this, loader, "Zsolti", new MyFixtureDef(), BodyDef.BodyType.DynamicBody));

        /**
         * ÜTKÖZÉSFIGYELÉSEK
         * **/
        if(getActorWorldHelper() != null && getActorWorldHelper() instanceof Box2DWorldHelper) {
            ((Box2DWorldHelper) getActorWorldHelper()).addContactListener(new MyContactListener() {
                @Override
                public void beginContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {
                    if (otherHelper.getActor() instanceof Tank) {
                        /**
                         * TANK
                         * **/

                        if (superTime > 0) {
                            if (getStage() != null && getStage() instanceof GameStage)
                                ((GameStage) getStage()).isShakeScreen = true;
                            otherHelper.getBody().applyForceToCenter(new Vector2(5000, 1000), true);
                        }else {
                            setFps(0);
                            GameStage.isAct = false;
                            //((Box2DWorldHelper)getActorWorldHelper()).getBody().setType(BodyDef.BodyType.StaticBody);
                        }

                    } else if (otherHelper.getActor() instanceof Coin) {
                        /**
                         * PÉNZ
                         * **/

                    } else if (otherHelper.getActor() instanceof Mushroom) {
                        /**
                         * GOMBA
                         * **/

                        otherHelper.actor.remove();
                        superTime = 8;
                    } else if (otherHelper.getActor() instanceof Marancsics) {
                        /**
                         * MARANCSICS
                         * **/

                        ((Marancsics) otherHelper.getActor()).setFps(24);
                        otherHelper.getBody().applyForceToCenter(new Vector2(-600,0),true);
                        ((Box2DWorldHelper)getActorWorldHelper()).getBody().applyForceToCenter(new Vector2(700,0),true);
                    }
                }

                @Override
                public void endContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {
                    if (otherHelper.getActor() instanceof Tank) {
                        /**
                         * TANK
                         * **/

                        addTimer(new TickTimer(0.3f, false, new TickTimerListener() {
                            @Override
                            public void onTick(Timer sender, float correction) {
                                super.onTick(sender, correction);
                                if (getStage() != null && getStage() instanceof GameStage)
                                    ((GameStage) getStage()).isShakeScreen = false;
                            }
                        }));
                    }
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

    float superTime = 0;

    @Override
    public void act(float delta) {
        super.act(delta);
        if(superTime > 0){
            /**
             * SUPER ZSOLTI
             * **/
            superTime -= delta;
            if(getTextureAtlas() != game.getMyAssetManager().getTextureAtlas(SUPER_ZSOLTI_ATLAS))
                setTextureAtlas(game.getMyAssetManager().getTextureAtlas(SUPER_ZSOLTI_ATLAS));
        }
        else{
            /**
             * ÁTLAGOS ZSOLTI
             * **/
            superTime = 0;
            if(getTextureAtlas() != game.getMyAssetManager().getTextureAtlas(ZSOLTI_ATLAS))
                setTextureAtlas(game.getMyAssetManager().getTextureAtlas(ZSOLTI_ATLAS));
        }
    }
}
