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
import hu.hdani1337.marancsicsdash.SoundManager;
import hu.hdani1337.marancsicsdash.Stage.GameStage;

import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.SoundManager.hee;
import static hu.hdani1337.marancsicsdash.SoundManager.kickSound;
import static hu.hdani1337.marancsicsdash.Stage.GameStage.isAct;

public class Marancsics extends OneSpriteAnimatedActor {
    //region Marancsics típus Enum
    public enum MarancsicsType{
        MARANCSICS, BOX, CONSTRUCTOR, CORONA
    }
    //endregion
    //region AssetList
    public static final String MARANCSICS_ATLAS = "atlas/marancsics/marancsics.atlas";
    public static final String MARANCSICS_BOX = "atlas/marancsics/marancsicsBox.atlas";
    public static final String MARANCSICS_CONSTRUCTOR = "atlas/marancsics/marancsicsConstr.atlas";
    public static final String MARANCSICS_CORONA = "atlas/marancsics/marancsicsCoron.atlas";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(MARANCSICS_ATLAS);
        assetList.addSound(SoundManager.KICK_SOUND);
    }
    //endregion
    //region Változók
    private boolean playing;
    //endregion
    //region Konstruktorok
    public Marancsics(MyGame game, World world, WorldBodyEditorLoader loader) {
        super(game, MARANCSICS_ATLAS);
        setTexture(GameStage.selectedMarancsics);
        playing = false;
        setFps(12);
        setSize(getWidth()*0.011f, getHeight()*0.011f);
        setActorWorldHelper(new Box2DWorldHelper(world, this, loader, "Marancsics", new MyFixtureDef(), BodyDef.BodyType.DynamicBody));

        /**
         * ÜTKÖZÉSFIGYELÉSEK
         * **/
        if(getActorWorldHelper() != null && getActorWorldHelper() instanceof Box2DWorldHelper) {
            ((Box2DWorldHelper) getActorWorldHelper()).addContactListener(new MyContactListener() {
                @Override
                public void beginContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {
                    collisionTank(otherHelper);
                }

                @Override
                public void endContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {
                    endCollisionTank(otherHelper);
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

    public Marancsics(MyGame game, MarancsicsType type){
        super(game, MARANCSICS_ATLAS);
        setTexture(type);
        setFps(12);
    }
    //endregion
    //region Ütközésfigyelések metódusai
    private void collisionTank(Box2DWorldHelper otherHelper){
        if (otherHelper.getActor() instanceof Tank) {
            //Tankkal ütközés
            if (getStage() != null && getStage() instanceof GameStage)
                ((GameStage) getStage()).isShakeScreen = true;
            otherHelper.getBody().applyForceToCenter(new Vector2(5000, 1000), true);
            ((Tank)otherHelper.actor).kicked();
            if(!muted && !playing) {
                kickSound.play();
                hee.play();
                playing = true;
                addTimer(new TickTimer(0.15f,false,new TickTimerListener(){
                    @Override
                    public void onStop(Timer sender) {
                        super.onStop(sender);
                        playing = false;
                    }
                }));
            }
        }
    }

    private void endCollisionTank(Box2DWorldHelper otherHelper){
        if (otherHelper.getActor() instanceof Tank) {
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
    //endregion
    //region Act metódusai
    @Override
    public void act(float delta) {
        super.act(delta);
        if(getStage() != null && getStage() instanceof GameStage) {
            if (Zsolti.isDead) {
                setX(getX() + 0.25f);
                if (getRotation() != 0) setRotation(0);
            } else {
                if (getRotation() != 0) setRotation(0);
                if (getX() != 0.5f) setX(0.5f);
                if (!isAct) setY(Background.ground * 2);
            }
        }
    }
    //endregion
    //region Egyéb metódusok
    private void setTexture(MarancsicsType type){
        switch (type){
            case BOX:{
                setTextureAtlas(game.getMyAssetManager().getTextureAtlas(MARANCSICS_BOX));
                break;
            }
            case CORONA:{
                setTextureAtlas(game.getMyAssetManager().getTextureAtlas(MARANCSICS_CORONA));
                break;
            }
            case MARANCSICS:{
                setTextureAtlas(game.getMyAssetManager().getTextureAtlas(MARANCSICS_ATLAS));
                break;
            }
            case CONSTRUCTOR:{
                setTextureAtlas(game.getMyAssetManager().getTextureAtlas(MARANCSICS_CONSTRUCTOR));
                break;
            }
        }
    }
    //endregion
}
