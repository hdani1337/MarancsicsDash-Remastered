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

import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.SoundManager.coinSound;
import static hu.hdani1337.marancsicsdash.SoundManager.crashSound;
import static hu.hdani1337.marancsicsdash.SoundManager.kickSound;
import static hu.hdani1337.marancsicsdash.SoundManager.powerUpSound;

public class Zsolti extends OneSpriteAnimatedActor {

    public static final String ZSOLTI_ATLAS = "atlas/zsolti.atlas";
    public static final String SUPER_ZSOLTI_ATLAS = "atlas/superZsolti.atlas";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(ZSOLTI_ATLAS);
        assetList.addTextureAtlas(SUPER_ZSOLTI_ATLAS);
    }

    public static boolean isDead;
    public static boolean inAir;

    /**
     * Box2D konstruktor ContactListenerrel
     * **/
    public Zsolti(MyGame game, World world, WorldBodyEditorLoader loader) {
        super(game, ZSOLTI_ATLAS);
        setFps(12);
        setSize(getWidth()*0.011f, getHeight()*0.011f);
        setActorWorldHelper(new Box2DWorldHelper(world, this, loader, "Zsolti", new MyFixtureDef(), BodyDef.BodyType.DynamicBody));
        isDead = false;
        inAir = true;

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
                            isDead = true;
                            GameStage.isAct = false;
                            //((Box2DWorldHelper)getActorWorldHelper()).getBody().setType(BodyDef.BodyType.StaticBody);
                        }
                        if(!muted) crashSound.play();

                    } else if (otherHelper.getActor() instanceof Coin) {
                        /**
                         * PÉNZ
                         * **/
                        otherHelper.actor.remove();
                        Coin.coin++;
                        if(!muted) coinSound.play();
                    } else if (otherHelper.getActor() instanceof Mushroom) {
                        /**
                         * GOMBA
                         * **/

                        otherHelper.actor.remove();
                        superTime = 8;
                        if(!muted) powerUpSound.play();
                    } else if (otherHelper.getActor() instanceof Marancsics) {
                        /**
                         * MARANCSICS
                         * **/

                        ((Marancsics) otherHelper.getActor()).setFps(24);
                        otherHelper.getBody().applyForceToCenter(new Vector2(-900,0),true);
                        ((Box2DWorldHelper)getActorWorldHelper()).getBody().applyForceToCenter(new Vector2(700,0),true);
                        if(getStage() != null) {
                            if (!muted && getX() < getStage().getViewport().getWorldWidth() + 2)
                                kickSound.play();
                        }
                    } else if (otherHelper.getActor() instanceof SuperCoin){
                        /**
                         * SUPER COIN
                         * **/
                        otherHelper.actor.remove();
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
                    } else if(otherHelper.getActor() instanceof SuperCoin){
                        if(getStage() != null && getStage() instanceof GameStage)
                            ((GameStage)getStage()).addCoins();
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

    /**
     * Scene2D konstruktor
     * **/
    public Zsolti(MyGame game){
        super(game, ZSOLTI_ATLAS);
        setFps(12);
    }

    public float superTime = 0;

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

        /**
         * ZSOLTI NE BORULJON FEL AMÍG ÉL
         * **/
        if(getRotation() != 0 && !isDead)
            setRotation(0);

        if(getY() > Background.ground*2+0.25) {
            /**
             * ZSOLTI ELHAGYJA A TALAJT
             * **/
            inAir = true;
        }
        else {
            /**
             * ZSOLTI VISSZAESIK A TALAJRA VAGY NAGYON A KÖZELÉBE
             * **/
            inAir = false;
        }

        /**
         * FIGYELJÜK HOGY ZSOLTI A TALAJON VAN E VAGY SEM
         * **/
        if(getX() != 2.5f && !isDead && getStage() instanceof GameStage) {
            setX(2.5f);
            setOrigin(0,0);
            getActorWorldHelper().setBodyPosition(getX()+0.3f,getY());
        }
    }
}
